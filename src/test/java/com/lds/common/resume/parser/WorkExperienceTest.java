package com.lds.common.resume.parser;

import com.lds.common.resume.BaseTest;
import com.lds.common.resume.domain.WorkExperience;
import com.lds.common.resume.parser.WorkExperienceParser;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class WorkExperienceTest extends BaseTest {

    @Test
    public void oneWorkExperienceOn51job() throws Exception {
        File f = new File(basePath + "51job_简兆坚(57768818).doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOn51job1() throws Exception {
        File f = new File(basePath + "51job_曹鹏(49804639).doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOnBoss() throws Exception {
        File f = new File(basePath + "boss直聘何正宇3年.docx");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOnZhilianzhaopin() throws Exception {
        File f = new File(basePath + "智联招聘_温绍勇_Java研发工程师_中文_20191111_1573438920974.doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOnZhilianzhaopin1() throws Exception {
        File f = new File(basePath + "智联招聘_武静芳_ui设计师_中文_20191104_1572837406504.doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOnZhilianzhaopin2() throws Exception {
        File f = new File(basePath + "智联招聘_郗文丽_数据分析BI方向_中文_20191108_1573193615159.doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }
/**
 *@Description: 该解析的简历需要\r\n换行
 *@author Murray Law
 *@date 2019/12/11 13:44
 */
    @Test
    public void oneWorkExperienceOnZhilianzhaopin3() throws Exception {
        File f = new File(basePath + "智联招聘_胡程_软件测试工程师_中文_20191031_1572509741654.doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOnZhilianzhaopin4() throws Exception {
        File f = new File(basePath + "智联招聘_伍锡伟_中级java开发工程师_中文_20191106_1573022627922.doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOnZhilianzhaopin5() throws Exception {
        File f = new File(basePath + "智联招聘_温琦生_项目运营总监_中文_20191105_1572919559195.doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep : wps) {
            System.out.println("wep = " + wep);
        }
    }
}
