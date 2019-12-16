package com.lds.common.resume.parser;

import com.lds.common.resume.BaseTest;
import com.lds.common.resume.domain.BasicInfo;
import com.lds.common.resume.parser.BasicInfoParser;
import org.junit.Test;

import java.io.File;

public class BasicInfoParserTest extends BaseTest {


    @Test
    public void oneBaseInfoOn51job1() throws Exception {
        File f = new File(basePath + "51job_简兆坚(57768818).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOn51job2() throws Exception {
        File f = new File(basePath + "51job_黄伟康(496699083).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOn51job3() throws Exception {
        File f = new File(basePath + "51job_王维爱(492726471).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnBoss() throws Exception {
        File f = new File(basePath + "boss直聘何正宇3年.docx");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnZhiLian() throws Exception {
        File f = new File(basePath + "智联招聘_胡程_软件测试工程师_中文_20191031_1572509741654.doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnZhiLian1() throws Exception {
        File f = new File(basePath + "智联招聘_伍泉_高级Android开发工程师_中文_20191104_1572832055378.doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnZhiLian2() throws Exception {
        File f = new File(basePath + "智联招聘_伍祖辉_中级前端工程师_中文_20191105_1572919075535.doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnZhuoPin() throws Exception {
        File f = new File(basePath + "智联卓聘_中文_()_20191127 (7).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnZhuoPin1() throws Exception {
        File f = new File(basePath + "智联卓聘_中文_()_20191127 (4).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnZhuoPin2() throws Exception {
        File f = new File(basePath + "智联卓聘_中文_()_20191127 (3).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnRenCai() throws Exception {
        File f = new File(basePath + "运营专员张雅花(J9972636).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnRenCai1() throws Exception {
        File f = new File(basePath + "高级.NET工程师余正伟(J4511083).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }

    @Test
    public void oneBaseInfoOnRenCai2() throws Exception {
        File f = new File(basePath + "高级软件工程师吴财圣(J9982025).doc");
        String html = converHtml(f);
        BasicInfoParser infoParser = new BasicInfoParser(html);
        BasicInfo parse = infoParser.parse();

        System.out.println("parse = " + parse);
    }
}
