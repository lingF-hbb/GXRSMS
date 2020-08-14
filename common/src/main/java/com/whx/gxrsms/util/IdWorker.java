package com.whx.gxrsms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/3/6
 **/
public class IdWorker {

    public static String nextId() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssms");
        return format.format(new Date());
    }

    public static long nextLongId() {
        return Long.parseLong(nextId());
    }
}
