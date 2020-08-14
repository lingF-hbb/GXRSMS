package com.whx.gxrsms.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import java.io.*;
import java.util.Map;
import java.util.Objects;

/**
 * @Company lihfinance.com
 * @Author ZhaoShuai
 * Freemarker工具类
 * @Date Create in 2020/2/8
 **/
public class FreemarkerUtil {

    private static Configuration configuration;

    /**
     * @company lihfinance.com
     * @author create by ZhaoShuai in 2020/2/8
     *  模板数据填充
     * @param [template, map]
     * @return java.lang.String
     **/
    public static String templateSetData(String template, Map<String,Object> map) throws IOException, TemplateException {
        StringReader reader = new StringReader(template);
        return templateSetData(reader, map);
    }

    public static String templateSetData(Reader reader, Map<String, Object> map) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        if (Objects.isNull(configuration)) {
            configuration =new FreeMarkerConfigurationFactory().createConfiguration();
        }
        new Template("templates", reader, configuration).process(map, writer);
        String template = writer.toString();
        writer.close();
        reader.close();
        return template;
    }

    public static String templateSetData(InputStream input, Map<String, Object> map) throws IOException, TemplateException {
        InputStreamReader reader = new InputStreamReader(input, "utf-8");
        return templateSetData(reader, map);
    }
}
