package com.tongdao.conf;

/**
 * Created by zhengda on 2017/9/5.
 */
public class Config {

    // 服务端地址
    public static final String url = "http://127.0.0.1:10081";
//    public static final String url = "http://192.168.15.43:10081";

    // redis地址
    public static final String redisHost = "192.168.15.43";

    // linux 时间戳
    public static String epoch;

    // header中的认证信息
    public static String token ;

    // 随机用户信息
    public static String userName;
    public static String realName;
    public static String mobile;
    public static String email;
    public static int userId;

    //kaiguan
    public static boolean isTest = true;

}
