package com.lds.common.resume.parser;

import com.lds.common.resume.BaseTest;
import org.junit.Test;

import java.io.File;

/**
 * @author wx
 * @version 1.0.0
 * @className SkillsParserTest
 * @description TODO
 * @date 2019/12/6 15:05
 */
public class SkillsParserTest extends BaseTest {

    @Test
    public void oneSkillsParseZhiLian() throws Exception {
        File f = new File(basePath + "智联招聘_胡程_软件测试工程师_中文_20191031_1572509741654.doc");
        String html = converHtml(f);

        SkillsParser parser = new SkillsParser(html);
        parser.parse().forEach(skills -> {
                    System.out.println(skills.toString());
                }
        );
    }

    @Test
    public void oneSkillsParseZhiLian0() throws Exception {
        File f = new File(basePath + "智联招聘_温琦生_项目运营总监_中文_20191105_1572919559195.doc");
        String html = converHtml(f);

        SkillsParser parser = new SkillsParser(html);
        parser.parse().forEach(skills -> {
                    System.out.println(skills.toString());
                }
        );
    }

    @Test
    public void oneSkillsParse51Job() throws Exception {
        File f = new File(basePath + "51job_凌梦之(23234904).doc");
        String html = converHtml(f);

        SkillsParser parser = new SkillsParser(html);
        parser.parse().forEach(skills -> {
                    System.out.println(skills.toString());
                }
        );
    }

    @Test
    public void oneSkillsParseZhuoPin() throws Exception {
        File f = new File(basePath + "智联卓聘_中文_()_20191127 (4).doc");
        String html = converHtml(f);

        SkillsParser parser = new SkillsParser(html);
        parser.parse().forEach(skills -> {
                    System.out.println(skills.toString());
                }
        );
    }

    @Test
    public void oneSkillsParseZhuoPin1() throws Exception {
        File f = new File(basePath + "智联卓聘_中文_()_20191127 (9).doc");
        String html = converHtml(f);

        SkillsParser parser = new SkillsParser(html);
        parser.parse().forEach(skills -> {
                    System.out.println(skills.toString());
                }
        );
    }

    @Test
    public void oneSkillsParseZhuoPin2() throws Exception {
        File f = new File(basePath + "智联卓聘_中文20191101.doc");
        String html = converHtml(f);

        SkillsParser parser = new SkillsParser(html);
        parser.parse().forEach(skills -> {
                    System.out.println(skills.toString());
                }
        );
    }

    @Test
    public void oneSkillsParseZhuoPin3() throws Exception {
        File f = new File(basePath + "智联卓聘_中文_()_20191127 (3).doc");
        String html = converHtml(f);

        SkillsParser parser = new SkillsParser(html);
        parser.parse().forEach(skills -> {
                    System.out.println(skills.toString());
                }
        );
    }
}
