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
        File f = new File(basePath + "51job_凌梦之(23234904).doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep: wps){
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOn51job1() throws Exception {
        File f = new File(basePath + "51job_王维爱(492726471).doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep: wps){
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOnBoss() throws Exception {
        File f = new File(basePath + "boss直聘何正宇3年.docx");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep: wps){
            System.out.println("wep = " + wep);
        }
    }

    @Test
    public void oneWorkExperienceOnZhilianzhaopin() throws Exception {
        File f = new File(basePath + "智联招聘_胡程_软件测试工程师_中文_20191031_1572509741654.doc");
        String html = converHtml(f);
        WorkExperienceParser parser = new WorkExperienceParser(html);
        List<WorkExperience> wps = parser.parse();
        for (WorkExperience wep: wps){
            System.out.println("wep = " + wep);
        }
    }
}
