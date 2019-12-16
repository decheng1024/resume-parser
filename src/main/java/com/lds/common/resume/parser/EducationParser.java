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

        //boss\卓聘\人才热线
        educationEles = root.getElementsMatchingOwnText("^教育背景$");
        if (educationEles.size() > 0) {
            paragraphOZhuoPinAndRenCaiAndBoss(educations, educationEles);
        }
        return educations;
    }

    //boss\卓聘\人才热线
    private List<Education> paragraphOZhuoPinAndRenCaiAndBoss(List<Education> educations, Elements educationEles) {
        //卓聘
        if (educationEles.get(0).getElementsByAttributeValue("style", "font-size: 14px;").size() > 0) {
            Education education = new Education();
            Elements nextEles = educationEles.get(0).parent().parent().parent().parent().parent().nextElementSiblings();
            for (Element nextEleByZhuoPin : nextEles) {
                String[] arr = nextEleByZhuoPin.text().split(" \\| ");
                if (arr.length < 4) {
                    break;
                }
                String[] arr1 = arr[0].split(" -- ");
                education.setStartDate(arr1[0]);
                education.setEndDate(arr1[1]);
                education.setUniversity(arr[1]);
                education.setMajor(arr[2]);
                education.setDegree(arr[3]);
                if (arr.length > 4) {
                    education.setDescription(arr[4]);
                }
                educations.add(education);
            }
            return educations;
        }
        //人才热线
        Element educationEle = educationEles.first();
        if (educationEle.getElementsByAttributeValue("style", "font:12px/20px Arial; color:#ffffff; padding:0 25px; height:20px;").size() > 0) {
            Elements educationChildrenEles = educationEle.parent().nextElementSibling().child(0).children();
            educationChildrenEles.stream().forEach(educationChildrenEle -> {
                if (!"".equals(educationChildrenEle.text())) {
                    Element educationGrandsonEles = educationChildrenEle.child(0);
                    Education education = new Education();
                    Element schoolAndDegreeAndTime = educationGrandsonEles.child(0).child(0);
                    education.setUniversity(schoolAndDegreeAndTime.child(0).text());
                    education.setDegree(schoolAndDegreeAndTime.child(1).text());
                    String[] arr = schoolAndDegreeAndTime.child(2).text().split("至");
                    education.setStartDate(arr[0]);
                    education.setEndDate(arr[1]);
                    education.setMajor(educationGrandsonEles.child(1).text());
                    education.setDescription(educationGrandsonEles.child(2).text());
                    educations.add(education);
                }
            });
            return educations;
        }
        //boss
        Element nextEle;
        nextEle = educationEles.first();
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
        return educations;
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
                        if (StringUtils.isNotBlank(major)) {
                            education.setMajor(major);
                        }
                        Elements mojorEles = td.getElementsByAttributeValue("style", "padding-left:7px;");
                        if (mojorEles.size() > 0) {
                            major = mojorEles.first().text();
                            education.setMajor(major.split("\\|")[1]);
                        }


                        //专业描述
                        if ("专业描述：".equals(td.text())) {
                            education.setDescription(td.nextElementSibling().text());
                        }
                    });
                    educations.add(education);
                }
            });
            return educations;
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
