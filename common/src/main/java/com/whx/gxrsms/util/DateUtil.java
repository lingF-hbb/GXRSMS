package com.whx.gxrsms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ZhaoShuai
 * @date Create in 2020/5/3
 **/
public class DateUtil {

    public static String formatString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
