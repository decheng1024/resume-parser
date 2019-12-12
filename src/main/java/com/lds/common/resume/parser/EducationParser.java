package com.lds.common.resume.parser;

import com.lds.common.resume.domain.Education;
import com.lds.common.resume.util.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wx
 * @version 1.0.0
 * @className EducationParser
 * @description 教育经历解析
 * @date 2019/12/6 14:52
 */
public class EducationParser extends BaseParser {

    public EducationParser(String content) {
        super(content);
    }

    public List<Education> parse() {
        List<Education> educations = new ArrayList<>();
        //51job和智联
        Elements educationEles = root.getElementsMatchingOwnText("^教育经历$");
        if (educationEles.size() > 0) {
            return classOn51jobAndZhiLian(educationEles);
        }
        //boos
        educationEles = root.getElementsMatchingOwnText("^教育背景$");
        if (educationEles.size() > 0) {
            paragraphOnBoss(educations, educationEles);
        }
        return educations;
    }

    //boss
    private void paragraphOnBoss(List<Education> educations, Elements educationEles) {
        Element nextEle = educationEles.first();
        Pattern compile = Pattern.compile("\\d{2,4}.\\d{1,2}-\\d{2,4}.\\d{1,2}");

        while ((nextEle = nextEle.nextElementSibling()) != null) {
            Education education = null;
            Elements spanEles = nextEle.getElementsByTag("span");
            for (Element span : spanEles) {
                String text = span.text();
                if (StringUtils.isNotBlank(text)) {
                    Matcher matcher = compile.matcher(text);
                    if (matcher.find()) {
                        education = new Education();
                        String[] educationTimeArr = matcher.group().split("-");
                        education.setStartDate(educationTimeArr[0]);
                        education.setEndDate(educationTimeArr[1]);
                    }
                    if (text.contains("学院") || text.contains("大学")) {
                        education.setUniversity(text);
                    }
                    String degree = super.matchDegree(text);
                    if (StringUtils.isNotBlank(degree)) {
                        education.setDegree(degree);
                    }
                    String major = Major.getMajor(text);
                    if (StringUtils.isNotBlank(major)) {
                        education.setMajor(major);
                        educations.add(education);
                    }
                }
            }
        }
    }

    //51job和智联
    private List<Education> classOn51jobAndZhiLian(Elements educationEles) {
        List<Education> educations = new ArrayList<>();
        Element educationEleParent = educationEles.first().parent().nextElementSibling();
        Elements educationEle = educationEleParent.getElementsByAttributeValue("class", "p15");
        if (educationEle.size() > 0) {
            educationEle.stream().forEach(element -> {
                Elements tds = element.getElementsByTag("td");
                if (tds.size() > 0) {
                    Education education = new Education();
                    tds.stream().forEach(td -> {
                        String text = td.text();
                        Elements workTime = td.getElementsMatchingOwnText("\\d{2,4}/\\d{1,2}-\\d{2,4}/\\d{1,2}");
                        if (workTime.size() > 0) {
                            String[] workTimeArr = workTime.text().split("-");
                            education.setStartDate(workTimeArr[0]);
                            education.setEndDate(workTimeArr[1]);
                        }
                        if (text.contains("学院") || text.contains("大学") || text.contains("学校")) {
                            education.setUniversity(text);
                        }
                        //学历
                        String degree = matchDegree(text);
                        if (StringUtils.isNotBlank(degree)) {
                            education.setDegree(degree);
                        }
                        //专业
                        String major = Major.getMajor(text);
                        Elements mojorEles = td.getElementsByAttributeValue("style", "padding-left:7px;");
                        if (mojorEles.size() > 0) {
                            major = mojorEles.get(0).text();
                            education.setMajor(major);
                        }
                        if (StringUtils.isNotBlank(major)) {
                            education.setMajor(major);
                        }

                        //专业描述
                        if ("专业描述：".equals(td.text())) {
                            education.setDescription(td.nextElementSibling().text());
                        }
                    });
                    educations.add(education);
                }
            }); return educations;
        }
        //智联
        educationEleParent = educationEles.first().parent().parent().nextElementSibling();
        educationEle = educationEleParent.getElementsByAttributeValue("width", "710");
        if (educationEle.size() > 0) {
            Education education = new Education();
            String[] educationArr = educationEle.first().text().split(" ");
            education.setStartDate(educationArr[0]);
            education.setEndDate(educationArr[2]);
            education.setUniversity(educationArr[3]);
            education.setMajor(educationArr[4]);
            education.setDegree(educationArr[5]);
            educations.add(education);
            return educations;
        }
        return educations;
    }
}
