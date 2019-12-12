package com.lds.common.resume.processor;

import com.lds.common.resume.BaseTest;
import com.lds.common.resume.ResumeParseUtils;
import com.lds.common.resume.io.InputStreamCacher;
import com.lds.common.resume.util.EncodingDetectUtils;
import com.lds.common.resume.util.Word2HtmlUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlConverterTest extends BaseTest {

    @Test
    public void oneGeneratorHtml() throws Exception {
        //D:\workspace\resume-parser\target\classes\resume\51job_全业萍(767697783).doc
        //D:\workspace\resume-parser\target\test-classes\resume\51job_全业萍(767697783).doc
        File f = new File(basePath + "51job_黄志崇(439114242).doc");
        converHtmlFile(f);
    }

    @Test
    public void oneGeneratorHtmlOnBoss() throws Exception {
        File f = new File(basePath + "boss直聘何正宇3年.docx");
        converHtmlFile(f);
    }
    @Test
    public void oneGeneratorHtmlOnZhaopin() throws Exception {
        File f = new File(basePath + "智联招聘_胡程_软件测试工程师_中文_20191031_1572509741654.doc");
        converHtmlFile(f);
    }

    @Test
    public void batchTest() throws Exception {
        String str = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "resume/";
        File file = new File(str);
        File[] files = file.listFiles();
        for (File f: files) {
            if (!f.isDirectory()) {
                try {
                    converHtmlFile(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void converHtmlFile(File f) throws Exception {
        InputStream in = new FileInputStream(f);
        InputStreamCacher inputStreamCacher = new InputStreamCacher(in);
        String content = ResumeParseUtils.converResume(inputStreamCacher.getInputStream());

        String pathStr = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(File.separator));
        pathStr = pathStr + File.separator + "html" + File.separator + f.getName().substring(0, f.getName().indexOf(".")) + ".html";


       //String chartset = EncodingDetectUtils.detect(inputStreamCacher.getInputStream());
        Word2HtmlUtils.writeFile(content, pathStr, null);
    }
    

}
