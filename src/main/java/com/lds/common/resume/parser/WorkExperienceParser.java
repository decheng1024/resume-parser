package com.lds.common.resume.parser;

import com.lds.common.resume.domain.WorkExperience;
import com.lds.common.resume.util.StringUtils;
import org.jsoup.Jsoup;
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

    /**
     * @return java.util.List<com.lds.common.resume.domain.WorkExperience>
     * @Description: 针对51Job和智联的简历解析, 准确率100%
     * @author Murray Law
     * @date 2019/12/11 14:05
     */
    public List<WorkExperience> parse() {
        List<WorkExperience> workExperiences = new ArrayList<>();
        //找到51job的技能特长模块抬头
        Elements workExperiencesElesBy51Job = root.getElementsMatchingOwnText("^工作经验$");
        //获取51Job的技能列表
        if (workExperiencesElesBy51Job.size() > 0 && workExperiencesElesBy51Job.first().getElementsByAttributeValue("class", "plate1").size() > 0) {
            Element workExperiencesParentEle = workExperiencesElesBy51Job.get(0).parent().nextElementSibling();
            //第一个工作经验
            Element firstWorkExperiencesParentEle = workExperiencesParentEle.child(0).child(0).child(0).child(0);
            WorkExperience workExperience = getWorkExperienceBy51Job(firstWorkExperiencesParentEle);
            workExperiences.add(workExperience);
            Elements workExperiencesParentEles = firstWorkExperiencesParentEle.nextElementSiblings();
            for (int i = 1; i < workExperiencesParentEles.size(); i += 2) {
                workExperiences.add(getWorkExperienceBy51Job(workExperiencesParentEles.get(i)));
            }
        }
        //找到智联招聘的技能特长抬头
        Elements workExperiencesElesByZhiLian = root.getElementsMatchingOwnText("^工作经历$");
        //获取智联招聘的技能列表
        if (workExperiencesElesByZhiLian.size() > 0) {
            Element workExperiencesParentEle = workExperiencesElesByZhiLian.get(0).parent().parent().nextElementSibling();
            while ("table".equals(workExperiencesParentEle.tagName())) {
                workExperiences.add(getWorkExperienceByZhiLian(workExperiencesParentEle));
                workExperiencesParentEle = workExperiencesParentEle.nextElementSibling();
            }
        }
        //找到卓聘的技能特长模块抬头
        Elements workExperiencesElesByZhuoPin = root.getElementsMatchingOwnText("^工作经验$");
        if (workExperiencesElesByZhuoPin.size() > 0 && workExperiencesElesByZhuoPin.first().getElementsByAttributeValue("style", "font-size: 14px;").size() > 0) {
            WorkExperience workExperience = new WorkExperience();
            Element webzp = workExperiencesElesByZhuoPin.first().parent().parent().parent().parent().parent().nextElementSibling();
            Elements dateAndName = webzp.getElementsByAttributeValue("style", "font-weight: bold; font-size: 12px");
            Elements description = webzp.getElementsByAttributeValue("style", "font-size: 12px; line-height: 18px; word-break: break-all;");

            while (dateAndName.size() > 0 || description.size() > 0) {
                if (dateAndName.size() > 0) {
                    String[] arr = dateAndName.text().split(" \\| ");
                    String[] arr1 = arr[0].split(" -- ");
                    workExperience.setStartDate(arr1[0]);
                    workExperience.setEndDate(arr1[1]);
                    workExperience.setCompany(arr[1]);
                    workExperience.setJobTitle(arr[2]);
                } else {
                    description.first().child(0).children().stream().forEach(desc -> {
                        Elements currentIndustry = desc.getElementsMatchingOwnText("行业类别：");
                        if (currentIndustry.size() > 0) {
                            workExperience.setCurrentIndustry(currentIndustry.text());


                            WorkExperience workExperienceCopy = new WorkExperience();
                            workExperiences.add(workExperienceCopy);
                        }
                    });
                }
                webzp = webzp.nextElementSibling();
                dateAndName = webzp.getElementsByAttributeValue("style", "font-weight: bold; font-size: 12px");
                description = webzp.getElementsByAttributeValue("style", "font-size: 12px; line-height: 18px; word-break: break-all;");

            }
        }
        return workExperiences;
    }

//    public List<WorkExperience> parse() {
//        List<WorkExperience> workExperiences = new ArrayList<>();
//        Elements workExperienceTag = root.getElementsMatchingOwnText("^工作经验$");
//        if (workExperienceTag.size() == 0) {
//            workExperienceTag = root.getElementsMatchingOwnText("^工作经历$");
//        }
//        Element workExperiencesParent = null;
//        if (workExperienceTag.size() > 0) {
//            workExperiencesParent = workExperienceTag.first().parent().nextElementSibling();
//            if (!Objects.isNull(workExperiencesParent)) {
//                Elements workExperienceEles = workExperiencesParent.getElementsByAttributeValue("class", "p15");
//                if (workExperienceEles.size() > 0) {
//                    return parseByClassOn51job(workExperienceEles);
//                }
//            }
//        }
//        if (!Objects.isNull(sourceType)) {
//            List<Element> tables = new ArrayList<>();
//            Element table = workExperiencesParent.parent().nextElementSibling();
//            while (!Objects.isNull(table) && table.tag().getName().equals("table")) {
//                tables.add(table);
//                table = table.nextElementSibling();
//            }
//            for (Element tb : tables) {
//                WorkExperience wp = new WorkExperience();
//                Elements tds = tb.getElementsByTag("td");
//                for (Element td : tds) {
//                    String text = td.text();
//                    if (StringUtils.isBlank(text)) {
//                        continue;
//                    }
//                    String workTimeRegEx = "\\d{2,4}/\\d{1,2}-\\d{2,4}/\\d{1,2}";
//                    Matcher matcher = Pattern.compile(workTimeRegEx).matcher(text);
//                    String workTime = "";
//                    if (matcher.find()) {
//                        workTime = matcher.group();
//                    }
//                    matcher = Pattern.compile("\\d{2,4}.\\d{1,2}\\s{1}-\\s{1}至今").matcher(text);
//                    if (matcher.find()) {
//                        workTime = matcher.group();
//                    }
//                    if (StringUtils.isNotBlank(workTime)) {
//                        String[] workTimeArr = workTime.split("-");
//                        wp.setStartDate(workTimeArr[0]);
//                        wp.setEndDate(workTimeArr[1]);
//                        String[] s = text.split(" ");
//                        if (s.length > 3) {
//                            wp.setCompany(s[3]);
//                        }
//                    }
//                    //公司行业
//                    String industry = com.lds.common.resume.parser.Industry.getIndustry(text);
//                    if (StringUtils.isNotBlank(industry)) {
//                        wp.setCurrentIndustry(industry);
//                    }
//                    //职位名称
//                    String jobTitle = Job.getJobTitles(text);
//                    if (StringUtils.isNotBlank(jobTitle)) {
//                        wp.setJobTitle(jobTitle);
//                    }
//                    if (td.text().contains("工作描述")) {
//                        Element workDescEle = td.nextElementSibling();
//                        String workDesc = workDescEle.text();
//                        if (StringUtils.isNotBlank(workDesc)) {
//                            wp.setDescription(workDesc);
//                        }
//                    }
//                }
//                workExperiences.add(wp);
//            }
//        }
//        return workExperiences;
//    }

//    private List<WorkExperience> parseByClassOn51job(Elements workExperienceEles) {
//        List<WorkExperience> workExperiences = new ArrayList<>();
//        Elements tables = workExperienceEles.first().getElementsByTag("table");
//        for (Element table : tables) {
//            WorkExperience wp = new WorkExperience();
//            Elements tds = table.getElementsByTag("td");
//            for (Element td : tds) {
//                Elements workTime = td.getElementsMatchingOwnText("\\d{2,4}/\\d{1,2}-\\d{2,4}/\\d{1,2}");
//                if (workTime.size() > 0) {
//                    String[] workTimeArr = workTime.text().split("-");
//                    wp.setStartDate(workTimeArr[0]);
//                    wp.setEndDate(workTimeArr[1]);
//                }
//                workTime = td.getElementsMatchingOwnText("\\d{2,4}/\\d{1,2}-至今");
//                if (workTime.size() > 0) {
//                    String[] workTimeArr = workTime.text().split("-");
//                    wp.setStartDate(workTimeArr[0]);
//                    wp.setEndDate(workTimeArr[1]);
//                }
//                //公司
//                Elements companySpan = td.getElementsByTag("span");
//                if (companySpan.size() > 0 && companySpan.first().getElementsMatchingOwnText("公司").size() > 0) {
//                    wp.setCompany(companySpan.text());
//                }
//                //公司行业
//                String industry = com.lds.common.resume.parser.Industry.getIndustry(td.text());
//                if (StringUtils.isNotBlank(industry)) {
//                    wp.setCurrentIndustry(industry);
//                }
//
//                //部门
//                Elements departmentEle = td.getElementsMatchingOwnText("\\S{2,5}部");
//                if (departmentEle.size() > 0) {
//                    wp.setDepartment(departmentEle.text());
//                }
//                //职位名称
//                String jobTitle = Job.getJobTitles(td.text());
//                if (StringUtils.isNotBlank(jobTitle)) {
//                    wp.setJobTitle(jobTitle);
//                }
//                String jobFunction = Job.getJobFunction(td.text());
//                if (StringUtils.isNotBlank(jobFunction)) {
//                    wp.setJobFunction(jobFunction);
//                }
//                if (td.text().contains("工作描述")) {
//                    Element workDescEle = td.nextElementSibling();
//                    String workDesc = workDescEle.html();
//                    if (StringUtils.isNotBlank(workDesc)) {
//                        wp.setDescription(workDesc);
//                    }
//                }
//            }
//            workExperiences.add(wp);
//        }
//        return workExperiences;
//    }

    private WorkExperience getWorkExperienceBy51Job(Element workExperienceElement) {
        WorkExperience workExperience = new WorkExperience();

        String[] timeArr = workExperienceElement.getElementsByAttributeValue("class", "time").get(0).text().split("-");
        workExperience.setStartDate(timeArr[0]);
        workExperience.setEndDate(timeArr[1]);
        workExperience.setCompany(workExperienceElement.getElementsByAttributeValue("class", "bold").text());
        workExperience.setCurrentIndustry(workExperienceElement.getElementsByAttributeValue("class", "rtbox").text().split("\\|")[0]);
        workExperience.setJobTitle(workExperienceElement.getElementsByAttributeValue("class", "txt3").text());
        workExperience.setDepartment(workExperienceElement.getElementsByAttributeValue("class", "bold txt3").text());
        workExperience.setDescription(workExperienceElement.getElementsByAttributeValue("class", "txt1").text());

        return workExperience;
    }

    private WorkExperience getWorkExperienceByZhiLian(Element workExperienceElement) {
        WorkExperience workExperience = new WorkExperience();
        Elements timeAndCompanyAndJobTitleAndSalary = workExperienceElement.getElementsByAttributeValue("style", "font-size:9.0pt;font-family:宋体;mso-ascii-font-family:\n" +
                "  Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;\n" +
                "  mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:Calibri;\n" +
                "  mso-hansi-theme-font:minor-latin");
        if (timeAndCompanyAndJobTitleAndSalary.size() == 0) {
            timeAndCompanyAndJobTitleAndSalary = workExperienceElement.getElementsByAttributeValue("style", "font-size:9.0pt;font-family:宋体;mso-ascii-font-family:\r\n" +
                    "  Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;\r\n" +
                    "  mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:Calibri;\r\n" +
                    "  mso-hansi-theme-font:minor-latin");
        }
        String[] timeAndCompanyArr = timeAndCompanyAndJobTitleAndSalary.get(0).text().split(" ");
        String[] jobTitleAndSalary = timeAndCompanyAndJobTitleAndSalary.get(1).text().split(" \\| ");
        Elements currentIndustryAndDescription = workExperienceElement.getElementsByAttributeValue("style", "font-size:9.0pt;\n" +
                "  font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;\n" +
                "  mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:\n" +
                "  Calibri;mso-hansi-theme-font:minor-latin");
//        部分简历解析换行符为/r/n
        if (currentIndustryAndDescription.size() == 0) {
            currentIndustryAndDescription = workExperienceElement.getElementsByAttributeValue("style", "font-size:9.0pt;\r\n" +
                    "  font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;\r\n" +
                    "  mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:\r\n" +
                    "  Calibri;mso-hansi-theme-font:minor-latin");
        }
        workExperience.setStartDate(timeAndCompanyArr[0]);
        workExperience.setEndDate(timeAndCompanyArr[2]);
        workExperience.setCompany(timeAndCompanyArr[3]);
        workExperience.setCurrentIndustry(currentIndustryAndDescription.get(0).text());
        workExperience.setJobTitle(jobTitleAndSalary[0]);
        workExperience.setSalary(jobTitleAndSalary[1]);
        workExperience.setDescription(currentIndustryAndDescription.get(2).text());

        return workExperience;
    }
}

