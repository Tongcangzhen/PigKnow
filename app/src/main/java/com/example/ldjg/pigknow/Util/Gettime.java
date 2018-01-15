package com.example.ldjg.pigknow.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by ldjg on 2018/1/7.
 */

public class Gettime {
    public static String getthistime() {
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }

//    public static BmobDate getBmobDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    }

    public static String getthisdate() {
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }
}
