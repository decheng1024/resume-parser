package com.lds.common.resume.parser;

import com.lds.common.resume.domain.Skills;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 技能/语言的解析类
 * @author:MuweiLaw
 * @Date:2019/12/8 13:27
 */

public class SkillsParser extends BaseParser {
    public SkillsParser(String content) {
        super(content);
    }

    public List<Skills> parse() {
        ArrayList<Skills> list = new ArrayList<>();
        //找到51job的技能特长模块全部元素
        Elements skills51Eles = root.getElementsMatchingOwnText("^技能特长$");
        //找到智联证书模块全部元素
        Elements zhiLianCertificatCeEles = root.getElementsMatchingOwnText("^证书$");
        //找到智联语言能力模块全部元素和卓聘的抬头
        Elements zhiLianLanguageEles = root.getElementsMatchingOwnText("^语言能力$");
        //找到智联专业技能模块全部元素
        Elements zhiLianSkillsEles = root.getElementsMatchingOwnText("^专业技能$");
        //找到卓聘语言能力的抬头
        Elements zhuoPinLanguageEles = root.getElementsMatchingOwnText("^语言能力$");
        //找到卓聘专业技能模块全部元素
        Elements zhuoPinSkillsEles = root.getElementsMatchingOwnText("^计算机/IT技能$");
        //找到人才热线的专业技能模块全部元素
        Elements renCaiSkillsEles = root.getElementsMatchingOwnText("^技能专长$");

        //获取智联和卓聘的证书列表
        if (zhiLianCertificatCeEles.size() > 0) {
            if (zhiLianCertificatCeEles.first().getElementsByAttributeValue("style", "font-size:15.0pt;font-family:宋体;mso-ascii-font-family:\n" +
                    "Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:\n" +
                    "minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin;\n" +
                    "background:#D9D9D9;mso-shading:white;mso-pattern:gray-15 auto").size() > 0 || zhiLianCertificatCeEles.first().getElementsByAttributeValue("style", "font-size:15.0pt;font-family:宋体;mso-ascii-font-family:\r\n" +
                    "Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:\r\n" +
                    "minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin;\r\n" +
                    "background:#D9D9D9;mso-shading:white;mso-pattern:gray-15 auto").size() > 0) {
                Element zhiLianCertificatCeEle = zhiLianCertificatCeEles.first().parent().parent().nextElementSibling();
                //智联
                zhiLianCertificatCeEles = zhiLianCertificatCeEle.getElementsByAttributeValue("style", "font-size:9.0pt;font-family:\n" +
                        "  宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:\n" +
                        "  宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:Calibri;\n" +
                        "  mso-hansi-theme-font:minor-latin");
                zhiLianCertificatCeEles.stream().forEach(certificatCeEle -> {
                    String[] arr = certificatCeEle.text().split(" ");
                    Skills skills = new Skills();
                    skills.setCertificateDate(arr[0]);
                    skills.setSkillOrCertificateName(arr[1]);
                    list.add(skills);
                });
                //智联1
                zhiLianCertificatCeEles = zhiLianCertificatCeEle.getElementsByAttributeValue("style", "font-size:9.0pt;font-family:\r\n" +
                        "  宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:\r\n" +
                        "  宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:Calibri;\r\n" +
                        "  mso-hansi-theme-font:minor-latin");
                zhiLianCertificatCeEles.stream().forEach(certificatCeEle -> {
                    String[] arr = certificatCeEle.text().split(" ");
                    Skills skills = new Skills();
                    skills.setCertificateDate(arr[0]);
                    skills.setSkillOrCertificateName(arr[1]);
                    list.add(skills);
                });
            }
        }
        //获取智联的语言能力列表
        if (zhiLianLanguageEles.size() > 0) {
            if (zhiLianLanguageEles.first().getElementsByAttributeValue("style", "font-size:15.0pt;font-family:宋体;mso-ascii-font-family:\n" +
                    "Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:\n" +
                    "minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin;\n" +
                    "background:#D9D9D9;mso-shading:white;mso-pattern:gray-15 auto").size() > 0 || zhiLianLanguageEles.first().getElementsByAttributeValue("style", "font-size:15.0pt;font-family:宋体;mso-ascii-font-family:\r\n" +
                    "Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:\r\n" +
                    "minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin;\r\n" +
                    "background:#D9D9D9;mso-shading:white;mso-pattern:gray-15 auto").size() > 0) {
                //智联
                Element zhiLianLanguageEle = zhiLianLanguageEles.get(0).parent().parent().nextElementSibling();
                zhiLianLanguageEles = zhiLianLanguageEle.getElementsByAttributeValue("style", "font-size:9.0pt;\n  font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;\n  mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:\n  Calibri;mso-hansi-theme-font:minor-latin");
                zhiLianLanguageEles.stream().forEach(languageEles -> {
                    String[] arr = languageEles.text().split("： ");
                    Skills skills = new Skills();
                    skills.setSkillOrCertificateName(arr[0]);
                    skills.setSkillProficiency(arr[1]);
                    list.add(skills);
                });
                //智联1
                zhiLianLanguageEles = zhiLianLanguageEle.getElementsByAttributeValue("style", "font-size:9.0pt;\r\n  font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;\r\n  mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:\r\n  Calibri;mso-hansi-theme-font:minor-latin");
                zhiLianLanguageEles.stream().forEach(languageEles -> {
                    String[] arr = languageEles.text().split("： ");
                    Skills skills = new Skills();
                    skills.setSkillOrCertificateName(arr[0]);
                    skills.setSkillProficiency(arr[1]);
                    list.add(skills);
                });
            }

        }
        //获取卓聘的语言能力列表
        if (zhuoPinLanguageEles.size() > 0) {
            if (zhuoPinLanguageEles.first().getElementsByAttributeValue("style", "font-weight: bold;font-size:12px;").size() > 0) {
                Element zhuoPinLanguageEle = zhuoPinLanguageEles.first().parent().parent().parent().parent().parent().nextElementSibling();
                zhuoPinLanguageEle.child(0).child(0).children().stream().forEach(zhiLianSkillsChild -> {
                    Skills skills = new Skills();
                    String[] arr = zhuoPinLanguageEle.text().split(" \\| ");
                    String[] arr1 = arr[0].split("：");
                    skills.setSkillOrCertificateName(arr1[1]);
                    skills.setSkillProficiency(arr[1] + "/" + arr[2]);
                    list.add(skills);
                });
            }
            //人才热线
            if (zhuoPinLanguageEles.first().getElementsByAttributeValue("style", "font:12px/20px Arial; color:#ffffff; padding:0 25px; height:20px;").size() > 0) {
                Elements elements = zhuoPinLanguageEles.first().parent().nextElementSibling().child(0).child(0).child(0).children();
                elements.stream().forEach(element -> {
                    Skills skills = new Skills();
                    String[] arr = element.text().split(" ");
                    skills.setSkillOrCertificateName(arr[0]);
                    skills.setSkillProficiency(arr[1] + "/" + arr[2]);
                    list.add(skills);
                });

            }
        }


        //获取智联的技能特长列表
        if (zhiLianSkillsEles.size() > 0 && (zhiLianSkillsEles.first().

                getElementsByAttributeValue("style", "font-size:15.0pt;font-family:宋体;mso-ascii-font-family:\n" +
                        "Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:\n" +
                        "minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin;\n" +
                        "background:#D9D9D9;mso-shading:white;mso-pattern:gray-15 auto").

                size() > 0 || zhiLianSkillsEles.first().

                getElementsByAttributeValue("style", "font-size:15.0pt;font-family:宋体;mso-ascii-font-family:\r\n" +
                        "Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:\r\n" +
                        "minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin;\r\n" +
                        "background:#D9D9D9;mso-shading:white;mso-pattern:gray-15 auto").

                size() > 0)) {
            zhiLianSkillsEles = zhiLianSkillsEles.get(0).parent().parent().nextElementSibling().getElementsByTag("span");
            if (zhiLianSkillsEles.size() > 0) {
                for (int i = 0; i < zhiLianSkillsEles.size(); i += 2) {
                    String[] arr = zhiLianSkillsEles.get(i).text().split("：");
                    Skills skills = new Skills();
                    skills.setSkillOrCertificateName(arr[0]);
                    if (arr.length > 1) {
                        skills.setSkillProficiency(arr[1]);
                    }
                    list.add(skills);
                }

            }
        }
        //获取51Job的技能列表
        if (skills51Eles.size() > 0 && skills51Eles.first().getElementsByAttributeValue("class", "plate1").size() > 0) {
            Element educationEleParent = skills51Eles.first().parent().nextElementSibling();
            Elements skillOrCertificateNameEle = educationEleParent.getElementsByAttributeValue("style", "text-align:right;width:120px;font-size:13px;font-weight:bold;color:#666;word-break:break-all;");
            Elements skillProficiencyEle = educationEleParent.getElementsByAttributeValue("class", "skco");
            skillOrCertificateNameEle.stream().forEach(element -> {
                Skills skills = new Skills();
                skills.setSkillOrCertificateName(Jsoup.parse(element.toString()).text());
                list.add(skills);
            });
            String[] arr1 = Jsoup.parse(skillProficiencyEle.toString()).text().split(" ");
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSkillProficiency(arr1[i]);
            }
            Elements certificateEles = educationEleParent.getElementsMatchingOwnText("\\d{2,4}/\\d{1,2}");
            if (certificateEles.size() > 0) {
                if (educationEleParent.getElementsMatchingOwnText("\\d{2,4}/\\d{1,2}-\\d{2,4}/\\d{1,2}").size() == 0) {
                    certificateEles.stream().forEach(element -> {
                        Skills skills = new Skills();
                        skills.setCertificateDate(Jsoup.parse(element.toString()).text());
                        skills.setSkillOrCertificateName(element.nextElementSibling().text());
                        list.add(skills);
                    });
                }
            }
        }
        //获取卓聘的技能列表
        if (zhuoPinSkillsEles.size() > 0) {
            if (zhuoPinSkillsEles.first().getElementsByAttributeValue("style", "font-weight: bold;font-size:12px;").size() > 0) {
                Element zhuoPinSkillsEle = zhuoPinSkillsEles.first().parent().parent().parent().parent().parent().nextElementSibling();
                Elements zhiLianSkillsChildrenEles = zhuoPinSkillsEle.child(0).child(0).child(0).children();
                zhiLianSkillsChildrenEles.stream().forEach(zhiLianSkillsChild -> {
                    Skills skills = new Skills();
                    String[] arr = zhiLianSkillsChild.text().split(" \\| ");
                    String[] arr1 = (arr[0] + "：" + arr[1] + "：" + arr[2]).split("：");
                    skills.setSkillOrCertificateName(arr1[1]);
                    skills.setSkillProficiency(arr1[5]);
                    skills.setUsageTime(arr1[3]);
                    list.add(skills);
                });
            }
        }
        //获取人才热线的技能列表
        if (renCaiSkillsEles.size() > 0 && renCaiSkillsEles.first().getElementsByAttributeValue("style", "font:12px/20px Arial; color:#ffffff; padding:0 25px; height:20px;").size() > 0) {
            Element renCaiSkillsEle = renCaiSkillsEles.first().parent().nextElementSibling();
            String[] arr = renCaiSkillsEle.getElementsByAttributeValue("style", "font:12px/20px Arial; color:#333333;").first().html().split("<br>");
            for (int i = 0; i < arr.length; i++) {
                Skills skills = new Skills();
                skills.setSkillOrCertificateName(arr[i]);
                list.add(skills);

            }
        }
        return list;
    }
}

