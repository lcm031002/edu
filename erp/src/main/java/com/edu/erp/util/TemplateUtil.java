package com.edu.erp.util;

import com.edu.common.constants.Constants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * 模板工具类
 */
public class TemplateUtil {
    private static final String FTL = "/ftl";

    private TemplateUtil() {}

    private static class TemplateUtilHolder {
        private static TemplateUtil templateUtil = new TemplateUtil();
    }

    public static TemplateUtil getInstance() {
        return TemplateUtilHolder.templateUtil;
    }

    /**
     * 获取模板内容
     * @param templateName 模板名称
     * @param dataMap 模板参数值
     * @return 模板内容
     */
    public  String getContent(String templateName, Map<String, Object> dataMap) throws Exception {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding(Constants.DEFAULT_ENCODING);
        configuration.setClassForTemplateLoading(this.getClass(), FTL);
        Writer stringWriter = new StringWriter();
        Template tpl = configuration.getTemplate(templateName);
        tpl.process(dataMap, stringWriter);
        return stringWriter.toString();
    }
}
