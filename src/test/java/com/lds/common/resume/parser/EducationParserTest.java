package com.lds.common.resume.parser;

import com.lds.common.resume.BaseTest;
import com.lds.common.resume.domain.BasicInfo;
import com.lds.common.resume.domain.Education;
import com.lds.common.resume.parser.EducationParser;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author wx
 * @version 1.0.0
 * @className EducationParserTest
 * @description TODO
 * @date 2019/12/6 15:05
 */
public class EducationParserTest extends BaseTest {

    @Test
    public void oneEducationParse0() throws Exception {
        File f = new File(basePath + "51job_黄志崇(439114242).doc");
        String html = converHtml(f);
        EducationParser parser = new EducationParser(html);
        System.out.println(parser.parse().toString());
    }

    @Test
    public void oneEducationParse1() throws Exception {
        File f = new File(basePath + "51job_简兆坚(57768818).doc");
        String html = converHtml(f);

        EducationParser parser = new EducationParser(html);
        System.out.println(parser.parse().toString());

    }

    @Test
    public void oneEducationParse2() throws Exception {
        File f = new File(basePath + "51job_黄子欣(492361147).doc");
        String html = converHtml(f);

        EducationParser parser = new EducationParser(html);
        System.out.println(parser.parse().toString());
    }

    @Test
    public void oneEducationZhiLian() throws Exception {
        File f = new File(basePath + "智联招聘_胡程_软件测试工程师_中文_20191031_1572509741654.doc");
        String html = converHtml(f);

        EducationParser parser = new EducationParser(html);
        System.out.println(parser.parse().toString());
    }

    @Test
    public void oneEducationZhiLian0() throws Exception {
        File f = new File(basePath + "智联招聘_温琦生_项目运营总监_中文_20191105_1572919559195.doc");
        String html = converHtml(f);

        EducationParser parser = new EducationParser(html);
        System.out.println(parser.parse().toString());
    }

    @Test
    public void oneBaseInfoOnBoss() throws Exception {
        File f = new File(basePath + "boss直聘何正宇3年.docx");
        String html = converHtml(f);
        EducationParser parser = new EducationParser(html);
        List<Education> educaton = parser.parse();

        System.out.println("educaton = " + educaton);
    }
}
