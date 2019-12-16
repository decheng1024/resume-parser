package com.lds.common.resume.parser;

import com.lds.common.resume.domain.BasicInfo;
import com.lds.common.resume.util.ChineseNumToArabicNumUtil;
import com.lds.common.resume.util.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wx
 * @version 1.0.0
 * @className BasicInfoParser
 * @description 基础信息解析
 * @date 2019/12/6 16:22
 */
public class BasicInfoParser extends BaseParser {

    public BasicInfoParser(String content) {
        super(content);
    }

    public BasicInfo parse() {

        BasicInfo basicInfo = new BasicInfo();

        basicInfo.setName(matchName());
        basicInfo.setMail(matchEmail());
        basicInfo.setPhone(matchPhone());
        basicInfo.setSex(matchSex());
        String birthday = matchBirthday(root);
        basicInfo.setBirthday(birthday);
        basicInfo.setAge(Integer.parseInt(matchAge(root)));
        basicInfo.setCurrentLocation(matchCurrentLocation());
        basicInfo.setDegree(matchDegree(root));
        basicInfo.setMaritalStatus(matchMaritalStatus());
        basicInfo.setYearOfWorkExperience(matchWorkExperienceLimit());
        //卓聘
        Elements zhuoPinEles = root.getElementsByAttributeValue("width", "582");
        if (zhuoPinEles.size() > 0) {
            basicInfo.setName(getBasicInfoByZhuoPin(zhuoPinEles, 0));
            String[] arr = getBasicInfoByZhuoPin(zhuoPinEles, 1).split(" \\| ");

            String[] arr1 = arr[3].split(" ");
            if (arr1[1].contains("年工作经验")) {
                basicInfo.setYearOfWorkExperience(arr1[0]);
            } else {
                basicInfo.setYearOfWorkExperience(arr1[1]);
            }

        }
        return basicInfo;
    }

    /**
     * @return java.lang.String
     * @Description: 匹配姓名
     * @author Murray Law
     * @date 2019/12/12 15:35
     */
    private String matchName() {
        //51job
        Elements nameElements = root.getElementsByClass("name");
        if (nameElements.size() > 0) {
            Element nameElement = nameElements.first();
            Elements spans = nameElement.getElementsByTag("span");
            if (spans.size() > 0) {
                for (int i = 0; i < spans.size(); i++) {
                    spans.get(i).remove();
                }
                return nameElement.text();
            }
        }
        //智联招聘
        Elements zhiLianEles = root.getElementsByAttributeValue("style", "text-align:left;tab-stops:366.0pt");
        if (zhiLianEles.size() > 0) {
            return zhiLianEles.get(0).text();
        }
        //人才热线
        Elements RenCaiEles = root.getElementsByAttributeValue("style", "padding:10px 0 10px 25px; font:24px/20px Arial; color:#333333;");
        if (RenCaiEles.size() > 0) {
            return RenCaiEles.get(0).text();
        }
        //boss
        Elements names = root.getElementsMatchingOwnText("姓名：");
        if (names.size() > 0) {
            return names.first().text().replace("姓名：", "");
        }
        return "";
    }

    /**
     * @return java.lang.String
     * @Description: 匹配电话号码
     * @author Murray Law
     * @date 2019/12/12 15:35
     */
    private String matchPhone() {
        String phone = "";
        String phoneRegEx = "1[3-9]{2}((\\d{8})|(\\*\\*\\*\\*\\d{4}))";
        Matcher matcher = Pattern.compile(phoneRegEx).matcher(content);
        if (matcher.find()) {
            phone = matcher.group();
        }

        return phone;
    }

    private String matchEmail() {
        String emailRegEx = "(\\w{0,}[0-9]{0,}|(\\w{0,}\\*\\*\\*\\*))@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Matcher matcher = Pattern.compile(emailRegEx).matcher(root.text());
        String email = "";
        if (matcher.find()) {
            email = matcher.group();
        }
        return email;
    }

    /**
     * @return java.lang.String
     * @Description: 匹配工作经验
     * @author Murray Law
     * @date 2019/12/12 15:36
     */
    private String matchWorkExperienceLimit() {
        String workExperienceLimit = "";
        String workExperienceLimitRegEx = "(\\d{1,2})(年工作经验|年 年工作经验)";
        Matcher matcher = Pattern.compile(workExperienceLimitRegEx).matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        workExperienceLimitRegEx = "工作经验：";
        Elements eles = root.getElementsMatchingOwnText(workExperienceLimitRegEx);
        if (eles.size() > 0) {
            workExperienceLimit = eles.first().text().replace("工作经验：", "").replace("年", "");
            if (ChineseNumToArabicNumUtil.isChineseNum(workExperienceLimit)) {
                return String.valueOf(ChineseNumToArabicNumUtil.chineseNumToArabicNum(workExperienceLimit));
            }
        }
        Elements renCaiEle = root.getElementsByAttributeValue("style", "font:14px/20px Arial; color:#333333;");
        if (renCaiEle.size() > 0) {
            return renCaiEle.last().text();
        }
        return workExperienceLimit;
    }

    private String matchMaritalStatus() {
        if (root.getElementsMatchingOwnText("已婚").size() > 0) {
            return "已婚";
        }
        if (root.getElementsMatchingOwnText("未婚").size() > 0) {
            return "未婚";
        }
        if (root.getElementsMatchingOwnText("离婚").size() > 0) {
            return "离婚";
        }
        if (root.getElementsMatchingOwnText("离异").size() > 0) {
            return "离异";
        }
        if (root.getElementsMatchingOwnText("丧偶").size() > 0) {
            return "丧偶";
        }
        return "--";
    }

    /**
     * 51job有当前位置
     * boss 没有
     *
     * @return 当前位置
     */
    private String matchCurrentLocation() {
        String currentLocationRegEx = "现居住\\s?[\\u4e00-\\u9fa5]{2,5}";
        Elements eles = root.getElementsMatchingOwnText(currentLocationRegEx);
        if (eles.size() > 0) {
            for (int i = 0; i < eles.size(); i++) {
                Element element = eles.get(i);
                Matcher matcher = Pattern.compile(currentLocationRegEx).matcher(element.text());
                if (matcher.find()) {
                    return matcher.group().replaceAll("现居住", "");
                }
            }
        }
        String location = Location.getAtMost3Location(content);
        if (StringUtils.isNotBlank(location)) {
            return location;
        }
        return "";
    }

    private String matchAge(Element root) {
        //51job
        String ageRegEx = "\\d{2}岁|(\\d{2} 岁)";
        String age = "0";
        Matcher matcher = Pattern.compile(ageRegEx).matcher(content);
        if (matcher.find()) {
            age = matcher.group();
            if (age.indexOf("岁") > -1) {
                return age.replaceAll("岁", "").replace(" ", "");
            }
        }
        //boss
        Elements eles = root.getElementsMatchingOwnText("年龄：");
        if (eles.size() > 0) {
            return eles.first().text().replace("年龄：", "");
        }
        return "0";
    }

    private String matchBirthday(Element root) {
        //51job
        String birthdayRegEx = "(\\d{4}年\\d{1,2}月\\d{1,2}日)";
        Matcher matcher = Pattern.compile(birthdayRegEx).matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        birthdayRegEx = "(\\d{4}年\\d{1,2}月)";
        matcher = Pattern.compile(birthdayRegEx).matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private String matchSex() {
        if (root.getElementsMatchingOwnText("男").size() > 0) {
            return "男";
        }
        if (root.getElementsMatchingOwnText("女").size() > 0) {
            return "女";
        }
        return "男";
    }

    /**
     * @return org.jsoup.nodes.Element
     * @Description: 根据子信息的索引[i]从卓聘个人信息模板elements中获取子信息文本
     * @Param [i]
     * @author Murray Law
     * @date 2019/12/12 15:50
     */
    private String getBasicInfoByZhuoPin(Elements elements, int i) {
        return elements.get(0).child(0).child(0).child(i).text();

    }
}
