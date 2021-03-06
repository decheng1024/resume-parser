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
public class ResumeparseUtilsTest extends BaseTest{

    @Test
    public void oneParseOnBoss() throws Exception {
        File f = new File(basePath + "boss直聘何正宇3年.docx");
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
    public void oneParseOnZhaopin() throws Exception {
        File f = new File(basePath + "智联招聘_胡程_软件测试工程师_中文_20191031_1572509741654.doc");
        String str = ResumeParseUtils.parseResume(f);
        System.out.println("str = " + str);
    }
}
