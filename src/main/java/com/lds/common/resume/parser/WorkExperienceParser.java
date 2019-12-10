package com.lds.common.resume.parser;

import com.lds.common.resume.domain.WorkExperience;
import com.lds.common.resume.util.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工作经验解析
 */
public class WorkExperienceParser extends BaseParser {

    public WorkExperienceParser(String content) {
        super(content);
    }

    public List<WorkExperience> parse() {
        List<WorkExperience> workExperiences = new ArrayList<>();
        Elements workExperienceTag = root.getElementsMatchingOwnText("^工作经验$");
        if (workExperienceTag.size() == 0) {
            workExperienceTag = root.getElementsMatchingOwnText("^工作经历$");
        }
        Element workExperiencesParent = null;
        if (workExperienceTag.size() > 0) {
            workExperiencesParent = workExperienceTag.first().parent().nextElementSibling();
            if (!Objects.isNull(workExperiencesParent)) {
                Elements workExperienceEles = workExperiencesParent.getElementsByAttributeValue("class", "p15");
                if (workExperienceEles.size() > 0) {
                    return parseByClassOn51job(workExperienceEles);
                }
            }
        }
        if( !Objects.isNull(sourceType)){
            List<Element> tables = new ArrayList<>();
            Element table = workExperiencesParent.parent().nextElementSibling();
            while ( !Objects.isNull(table) && table.tag().getName().equals("table")){
                tables.add(table);
                table = table.nextElementSibling();
            }
            for (Element tb : tables){
                WorkExperience wp = new WorkExperience();
                Elements tds = tb.getElementsByTag("td");
                for(Element td : tds){
                    String text = td.text();
                    if(StringUtils.isBlank(text)){
                        continue;
                    }
                    String workTimeRegEx = "\\d{2,4}/\\d{1,2}-\\d{2,4}/\\d{1,2}";
                    Matcher matcher = Pattern.compile(workTimeRegEx).matcher(text);
                    String workTime = "";
                    if (matcher.find()) {
                        workTime = matcher.group();
                    }
                    matcher = Pattern.compile("\\d{2,4}.\\d{1,2}\\s{1}-\\s{1}至今").matcher(text);
                    if (matcher.find()) {
                        workTime = matcher.group();
                    }
                    if(StringUtils.isNotBlank(workTime)){
                        String[] workTimeArr = workTime.split("-");
                        wp.setStartDate(workTimeArr[0]);
                        wp.setEndDate(workTimeArr[1]);
                        String[] s = text.split(" ");
                        if( s.length > 3){
                            wp.setCompany(s[3]);
                        }
                    }
                    //公司行业
                    String industry = com.lds.common.resume.parser.Industry.getIndustry(text);
                    if (StringUtils.isNotBlank(industry)) {
                        wp.setCurrentIndustry(industry);
                    }
                    //职位名称
                    String jobTitle = Job.getJobTitles(text);
                    if (StringUtils.isNotBlank(jobTitle)) {
                        wp.setJobTitle(jobTitle);
                    }
                    if (td.text().contains("工作描述")) {
                        Element workDescEle = td.nextElementSibling();
                        String workDesc = workDescEle.text();
                        if (StringUtils.isNotBlank(workDesc)) {
                            wp.setDescription(workDesc);
                        }
                    }
                }
                workExperiences.add(wp);
            }
        }
        return workExperiences;
    }

    private List<WorkExperience> parseByClassOn51job(Elements workExperienceEles) {
        List<WorkExperience> workExperiences = new ArrayList<>();
        Elements tables = workExperienceEles.first().getElementsByTag("table");
        for (Element table : tables) {
            WorkExperience wp = new WorkExperience();
            Elements tds = table.getElementsByTag("td");
            for (Element td : tds) {
                Elements workTime = td.getElementsMatchingOwnText("\\d{2,4}/\\d{1,2}-\\d{2,4}/\\d{1,2}");
                if (workTime.size() > 0) {
                    String[] workTimeArr = workTime.text().split("-");
                    wp.setStartDate(workTimeArr[0]);
                    wp.setEndDate(workTimeArr[1]);
                }
                workTime = td.getElementsMatchingOwnText("\\d{2,4}/\\d{1,2}-至今");
                if (workTime.size() > 0) {
                    String[] workTimeArr = workTime.text().split("-");
                    wp.setStartDate(workTimeArr[0]);
                    wp.setEndDate(workTimeArr[1]);
                }
                //公司
                Elements companySpan = td.getElementsByTag("span");
                if (companySpan.size() > 0 && companySpan.first().getElementsMatchingOwnText("公司").size() > 0) {
                    wp.setCompany(companySpan.text());
                }
                //公司行业
                String industry = com.lds.common.resume.parser.Industry.getIndustry(td.text());
                if (StringUtils.isNotBlank(industry)) {
                    wp.setCurrentIndustry(industry);
                }

                //部门
                Elements departmentEle = td.getElementsMatchingOwnText("\\S{2,5}部");
                if (departmentEle.size() > 0) {
                    wp.setDepartment(departmentEle.text());
                }
                //职位名称
                String jobTitle = Job.getJobTitles(td.text());
                if (StringUtils.isNotBlank(jobTitle)) {
                    wp.setJobTitle(jobTitle);
                }
                String jobFunction = Job.getJobFunction(td.text());
                if (StringUtils.isNotBlank(jobFunction)) {
                    wp.setJobFunction(jobFunction);
                }
                if (td.text().contains("工作描述")) {
                    Element workDescEle = td.nextElementSibling();
                    String workDesc = workDescEle.html();
                    if (StringUtils.isNotBlank(workDesc)) {
                        wp.setDescription(workDesc);
                    }
                }
            }
            workExperiences.add(wp);
        }
        return workExperiences;
    }
}

