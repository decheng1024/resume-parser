package com.lds.common.resume.parser;

import com.lds.common.resume.domain.SelfEvaluation;
import org.jsoup.select.Elements;

/**
 * @Description:
 * @author:MuweiLaw
 * @Date:2019/12/12 10:06
 */

public class SelfEvaluationParser extends BaseParser {
    public SelfEvaluationParser(String content) {
        super(content);
    }

    public SelfEvaluation parse() {
        SelfEvaluation selfEvaluation = new SelfEvaluation();
        selfEvaluation.setSelfEvaluation(matchSelfEvaluation());
        return selfEvaluation;
    }

    private String matchSelfEvaluation() {
        //智联
        Elements selfEvaluation = root.getElementsMatchingOwnText("^自我评价$");
        String sytle = "font-size:15.0pt;font-family:宋体;mso-ascii-font-family:\n" +
                "Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:\n" +
                "minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin;\n" +
                "background:#D9D9D9;mso-shading:white;mso-pattern:gray-15 auto";
        if (selfEvaluation.size() > 0 && (selfEvaluation.first().getElementsByAttributeValue("style", sytle)).size() > 0) {
            return selfEvaluation.get(0).parent().parent().nextElementSibling().text();
        }
        return "";
    }
}
