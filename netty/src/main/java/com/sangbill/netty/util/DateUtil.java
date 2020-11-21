package com.sangbill.netty.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static SimpleDateFormat yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String date2String(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    public static String date2String(Date date) {
        return yyyy_MM_dd_HH_mm_ss.format(date);
    }
}
