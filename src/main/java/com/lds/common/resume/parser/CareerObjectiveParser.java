package com.lds.common.resume.parser;

import com.lds.common.resume.domain.CareerObjective;
import com.lds.common.resume.util.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 职业目标分析器继承基础类
 */
public class CareerObjectiveParser extends BaseParser {

    /**
     * @return
     * @Param [content]
     * @Description:
     * @author Murray Law
     * @date 2019/12/5 17:56
     */
    public CareerObjectiveParser(String content) {
        super(content);
    }

    /**
     * @Param []
     * @Description 求职意向
     * @author Murray Law
     * @date 2019/12/5 17:56
     * @return com.lds.common.resume.domain.CareerObjective
     */
    public CareerObjective parse() {
        //职业目标
        CareerObjective careerObjective = new CareerObjective();
        //意向行业
        careerObjective.setExpectingIndustry(matchExpectingInsuatry());
        careerObjective.setExpectingSalary(matchExpectingSalary());
        careerObjective.setExpectingPosition(matchExpectingPosition());
        careerObjective.setExpectingLocation(matchExpectingLocation());
        careerObjective.setHiredate(matchHiredate());

        return careerObjective;
    }

    /**
     * @Description 意向行业
     * @author Murray Law
     * @date 2019/12/6 14:01
     * @return java.lang.String
     */
    private String matchExpectingInsuatry() {
        String industry = "";
        StringBuffer expectingIndustry = new StringBuffer();//期待行业
        Elements industrys = root.getElementsMatchingOwnText("求职意向");
        if (industrys.size() > 0) {
            Element parent = industrys.first().parent().parent();
            if (StringUtils.isNotBlank(parent.tagName()) && parent.tagName().toLowerCase().equals("tbody")) {
                industry = Industry.getIndustry(parent.text());
                return industry;
            }
            industry = Industry.getIndustry(industrys.first().text());
            if (StringUtils.isNotBlank(industry)) {
                return industry;
            }

        }
        Elements elements = root.getElementsMatchingOwnText("^行业：");
        if (elements.size() > 0) {
            return elements.get(0).nextElementSibling().text();
        }

        Elements tds = root.getElementsByTag("td");
        for (Element ele : tds){
            if( ele.getElementsMatchingOwnText("^期望从事行业：").size() > 0){
                Element element = ele.nextElementSibling();
                if( !Objects.isNull(element)){
                    return element.text();
                }
            }
        }
        return "";
    }


    /**
     * @return java.lang.String
     * @Param []
     * @Description 返回期望薪资
     * @author Murray Law
     * @date 2019/12/6 14:00
     */
    private String matchExpectingSalary() {
        String expectingSalary = "";
        String expectingYearSalaryRegEx = "\\d{1,4}-\\d{1,4}万元/年";

        Elements eles = root.getElementsMatchingOwnText(expectingYearSalaryRegEx);
        if (eles.size() > 0) {
            for (int i = 0; i < eles.size(); i++) {
                Element element = eles.get(i);
                Matcher matcher = Pattern.compile(expectingYearSalaryRegEx).matcher(element.text());
                if (matcher.find()) {
                    return matcher.group();
                }
            }
        }
        Elements salaryEles = root.getElementsMatchingOwnText("期望薪资：");
        if( salaryEles.size() > 0){
            return salaryEles.get(0).nextElementSibling().text();
        }
        Elements tds = root.getElementsByTag("td");
        for (Element ele : tds){
            if( ele.getElementsMatchingOwnText("^期望月薪：").size() > 0){
                Element element = ele.nextElementSibling();
                if( !Objects.isNull(element)){
                    return element.text();
                }
            }
        }
        return "";
    }

    /**
     * @return java.lang.String
     * @Param []
     * @Description: 期望职位
     * @author Murray Law
     * @date 2019/12/6 13:59
     */
    private String matchExpectingPosition() {
        return Job.getJobTitles(content);
    }

    /**
     * @return java.lang.String
     * @Param []
     * @Description: 期望工作地点
     * @author Murray Law
     * @date 2019/12/6 15:07
     */
    private String matchExpectingLocation() {
        String location = Location.getOneLocation(content);
        if (StringUtils.isNotBlank(location)) {
            return location;
        }
        Elements locationEles = root.getElementsMatchingOwnText("地点：");
        if( locationEles.size() > 0 ){
            return locationEles.first().nextElementSibling().text();
        }
        return "";
    }

    /**
     * @return java.lang.String
     * @Param []
     * @Description: 到岗时间
     * @author Murray Law
     * @date 2019/12/8 22:12
     */
    private String matchHiredate() {
        Elements elements = root.getElementsMatchingOwnText("到岗时间：");
        if (elements.size() > 0) {
            return elements.get(0).nextElementSibling().text();
        }
        Elements tds = root.getElementsByTag("td");
        for (Element ele : tds){
            if( ele.getElementsMatchingOwnText("^目前状况：").size() > 0){
                Element element = ele.nextElementSibling();
                if( !Objects.isNull(element)){
                    return element.text();
                }
            }
        }
        return "";
    }

    /**
     * @return java.lang.String
     * @Param []
     * @Description: 期望工作类型
     * @author Murray Law
     * @date 2019/12/8 22:57
     */
    private String matchJobType() {
        Elements elements = root.getElementsMatchingOwnText("工作类型：");
        if (elements.size() > 0) {
            return elements.get(0).nextElementSibling().text();
        }
        return "";
    }
}
