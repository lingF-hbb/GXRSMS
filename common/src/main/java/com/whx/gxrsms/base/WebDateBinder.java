package com.whx.gxrsms.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/3/2
 **/
public class WebDateBinder extends PropertyEditorSupport {

    private Logger logger = LoggerFactory.getLogger(WebDateBinder.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(text);
        } catch (ParseException e) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse(text);
            } catch (ParseException ex) {
                logger.error("日期格式转换异常,失败数据[{}]", text);
            }
        }
        if (!Objects.isNull(date)) {
            this.setValue(date);
        }
    }
}
