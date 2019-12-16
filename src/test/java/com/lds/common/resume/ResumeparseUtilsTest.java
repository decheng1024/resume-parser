package com.lds.common.resume;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author wx
 * @version 1.0.0
 * @className ResumeparseUtilsTest
 * @description TODO
 * @date 2019/12/6 16:49
 */
public class ResumeparseUtilsTest extends BaseTest {

    @Test
    public void oneParseOnBoss() throws Exception {
        File f = new File(basePath + "【UI  深圳10-14K】龙渊翔 3年.docx");
        String str = ResumeParseUtils.parseResume(new FileInputStream(f));
        System.out.println("str = " + str);
    }

    @Test
    public void oneParseOnBoss1() throws Exception {
        File f = new File(basePath + "【UI  深圳10-14K】刘芸琪 7年.pdf");
        String str = ResumeParseUtils.parseResume(new FileInputStream(f));
        System.out.println("str = " + str);
    }

    @Test
    public void oneParseOnBoss2() throws Exception {
        File f = new File(basePath + "【UI  深圳10-14K】刘雨晶 4年.doc");
        String str = ResumeParseUtils.parseResume(new FileInputStream(f));
        System.out.println("str = " + str);
    }

    @Test
    public void oneParseOn51Job() throws Exception {
        File f = new File(basePath + "51job_凌梦之(23234904).doc");
        String str = ResumeParseUtils.parseResume(new FileInputStream(f));
        System.out.println("str = " + str);
    }

    @Test
    public void oneParseOn51Job1() throws Exception {
        File f = new File(basePath + "51job_王维爱(492726471).doc");
        String str = ResumeParseUtils.parseResume(new FileInputStream(f));
        System.out.println("str = " + str);
    }

    @Test
    public void oneParseOn51Job2() throws Exception {
        File f = new File(basePath + "51job_黄子欣(492361147).doc");
        String str = ResumeParseUtils.parseResume(new FileInputStream(f));
        System.out.println("str = " + str);
    }

    @Test
    public void oneParseOnZhaopin() throws Exception {
        File f = new File(basePath + "智联招聘_胡程_软件测试工程师_中文_20191031_1572509741654.doc");
        String str = ResumeParseUtils.parseResume(f);
        System.out.println("str = " + str);
    }

    @Test
    public void oneParseOnZhaopin1() throws Exception {
        File f = new File(basePath + "智联招聘_奚钰格_运营专员_中文_20191108_1573202487643.doc");
        String str = ResumeParseUtils.parseResume(f);
        System.out.println("str = " + str);
    }

    @Test
    public void oneParseOnZhaopin2() throws Exception {
        File f = new File(basePath + "智联招聘_郗文丽_数据分析BI方向_中文_20191108_1573193615159.doc");
        String str = ResumeParseUtils.parseResume(f);
        System.out.println("str = " + str);
    }
    @Test
    public void oneParseOnRenCai() throws Exception {
        File f = new File(basePath + "运营专员张雅花(J9972636).doc");
        String str = ResumeParseUtils.parseResume(f);
        System.out.println("str = " + str);
    }@Test
    public void oneParseOnRenCai1() throws Exception {
        File f = new File(basePath + "高级软件工程师吴财圣(J9982025).doc");
        String str = ResumeParseUtils.parseResume(f);
        System.out.println("str = " + str);
    }@Test
    public void oneParseOnRenCai2() throws Exception {
        File f = new File(basePath + "高级.NET工程师余正伟(J4511083).doc");
        String str = ResumeParseUtils.parseResume(f);
        System.out.println("str = " + str);
    }
}
