package com.lds.common.resume.parser;

import com.lds.common.resume.domain.ProjectExperience;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wx
 * @version 1.0.0
 * @className ProjectExperienceParser
 * @description TODO
 * @date 2019/12/6 17:08
 */
public class ProjectExperienceParser extends BaseParser {

    public ProjectExperienceParser(String content) {
        super(content);
    }

    public List<ProjectExperience> parse() {
        List<ProjectExperience> projectExperiences = new ArrayList<>();
        Elements proectExperienceTag = root.getElementsMatchingOwnText("^项目经验$");
        Element proectExperiencesParent = null;
        if (proectExperienceTag.size() > 0) {
            //51job: 获取项目经验标签的下一个元素
            proectExperiencesParent = proectExperienceTag.first().parent().nextElementSibling();
            if (null != proectExperiencesParent) {
                return classParseFor51job(proectExperiencesParent);
            }

            Elements peEles = proectExperienceTag.first().nextElementSiblings();
            ProjectExperience pe = null;
            for (Element ele : peEles) {
                if (ele.text().contains("项目名称")) {
                    pe = new ProjectExperience();
                    pe.setProjectName(ele.text().replace("项目名称", "").replace(":", ""));
                }
                if (ele.text().contains("项目描述")) {
                    pe.setProjectDescription(ele.text().replace("项目描述", "").replace(":", ""));
                }
                if (ele.text().contains("项目职责")) {
                    pe.setDutyDescription(ele.text().replace("项目职责", "").replace(":", ""));
                    projectExperiences.add(pe);
                }
            }
        }
        proectExperienceTag = root.getElementsMatchingOwnText("^项目经历$");
        if (proectExperienceTag.size() > 0) {
            //智联: 获取项目经验标签的下一个元素
            proectExperiencesParent = proectExperienceTag.first().parent().parent().nextElementSibling();
            if (null != proectExperiencesParent) {
                return classParseForZhiLian(proectExperiencesParent);
            }
        }
        return projectExperiences;
    }

    private List<ProjectExperience> classParseFor51job(Element proectExperiencesParent) {
        List<ProjectExperience> projectExperiences = new ArrayList<>();
        Elements tba = proectExperiencesParent.getElementsByAttributeValue("class", "tba");
        if (tba.size() > 0) {
            tba.stream().forEach(ptd -> {
                ProjectExperience projectExperience = new ProjectExperience();
                Elements tds = ptd.getElementsByTag("td");
                tds.stream().forEach(td -> {
                    Elements workTime = td.getElementsMatchingOwnText("\\d{2,4}/\\d{1,2}-");
                    if (workTime.size() > 0) {
                        String[] workTimeArr = workTime.text().split("-");
                        projectExperience.setBeginDate(workTimeArr[0]);
                        projectExperience.setEndDate(workTimeArr[1]);
                        projectExperience.setProjectName(workTime.first().nextElementSibling().text());
                    }
                    Elements companyEles = td.getElementsMatchingOwnText("公司");
                    if (companyEles.size() > 0) {
                        if (null != companyEles.first().nextElementSibling()) {
                            projectExperience.setCompanyName(td.getElementsMatchingOwnText("公司").first().nextElementSibling().text());
                        }
                    }
                    Elements projectEles = td.getElementsMatchingOwnText("项目描述");
                    if (projectEles.size() > 0) {
                        if (null != projectEles.first().nextElementSibling()) {
                            projectExperience.setProjectDescription(projectEles.first().nextElementSibling().html());
                        }
                    }
                    projectEles = td.getElementsMatchingOwnText("工作描述");
                    if (projectEles.size() > 0) {
                        if (null != projectEles.first().nextElementSibling()) {
                            projectExperience.setProjectDescription(projectEles.first().nextElementSibling().html());
                        }
                    }
                    Elements dutyEles = td.getElementsMatchingOwnText("责任描述");
                    if (dutyEles.size() > 0) {
                        if (null != dutyEles.first().nextElementSibling()) {
                            projectExperience.setDutyDescription(dutyEles.first().nextElementSibling().html());
                        }
                    }
                });
                projectExperiences.add(projectExperience);
            });
        }
        return projectExperiences;
    }

    /**
     * @return java.util.List<com.lds.common.resume.domain.ProjectExperience>
     * @Param [proectExperiencesParent]
     * @Description:
     * @author Murray Law
     * @date 2019/12/11 19:56
     */
    private List<ProjectExperience> classParseForZhiLian(Element proectExperiencesParent) {
        List<ProjectExperience> projectExperiences = new ArrayList<>();
        Elements trs = proectExperiencesParent.child(0).children();
        ProjectExperience projectExperience = new ProjectExperience();
        for (Element tr : trs) {
            Elements dateAndName = tr.getElementsMatchingOwnText("\\d{2,4}.\\d{1,2} - ");
            if (dateAndName.size() > 0) {
                String[] dateAndNameArr = dateAndName.text().split(" ");
                projectExperience.setBeginDate(dateAndNameArr[0]);
                projectExperience.setEndDate(dateAndNameArr[2]);
                projectExperience.setProjectName(dateAndNameArr[3]);
            }
            Elements dutyDescription = tr.getElementsMatchingOwnText("^责任描述：$");
            if (dutyDescription.size() > 0) {
                projectExperience.setDutyDescription(dutyDescription.get(0).parent().parent().nextElementSibling().text());
            }
            Elements projectDescription = tr.getElementsMatchingOwnText("^项目描述：$");
            if (projectDescription.size() > 0) {
                projectExperience.setProjectDescription(projectDescription.get(0).parent().parent().nextElementSibling().text());
                ProjectExperience projectExperience1 = new ProjectExperience();
                projectExperience1.setBeginDate(projectExperience.getBeginDate());
                projectExperience1.setEndDate(projectExperience.getEndDate());
                projectExperience1.setProjectName(projectExperience.getProjectName());
                projectExperience1.setDutyDescription(projectExperience.getDutyDescription());
                projectExperience1.setProjectDescription(projectExperience.getProjectDescription());
                projectExperience1.setBeginDate(projectExperience.getBeginDate());
                projectExperiences.add(projectExperience1);
//                }
            }
        }
        return projectExperiences;
    }
}
