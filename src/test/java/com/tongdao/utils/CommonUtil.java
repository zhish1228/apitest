package com.tongdao.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongdao.conf.Config;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by zhengda on 2017/9/6.
 */
public class CommonUtil {


    /**
     *
     * 获取linux时间戳
     *
     * @return
     */
    public static String getEpoch(){
        return String.valueOf(System.currentTimeMillis());
    }


    /**
     * 通过时间戳获取redis中存储的验证码
     *
     * @param epoch
     * @return
     */
    public static String getVerifyCode(String epoch){
        Jedis jedis = new Jedis(Config.redisHost);
        String verifyCode = jedis.get(epoch);
        jedis.close();
        return verifyCode;
    }

    public static void log(String s){
        if(Config.isTest == true){
            System.out.println(s);
        }
    }


    private HttpUtil httpUtil = new HttpUtil();


    /**
     *
     * 查看swagger提供的接口信息，测试使用
     * @throws Exception
     */
    @Test
    public void showInterface() throws Exception{
        String s = httpUtil.doGet("/v2/api-docs", "");
        Map mapType = JSON.parseObject(s,Map.class);
        JSONObject jsonObject = (JSONObject) mapType.get("paths");
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            CommonUtil.log(entry.getKey());
        }
        CommonUtil.log(String.valueOf(jsonObject.size()));
    }

}

