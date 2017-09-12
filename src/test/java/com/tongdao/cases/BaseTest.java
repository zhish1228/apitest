package com.tongdao.cases;

import com.alibaba.fastjson.JSON;
import com.tongdao.conf.Config;
import com.tongdao.listener.TestListener;
import com.tongdao.utils.Common;
import com.tongdao.utils.HttpUtil;
import com.tongdao.utils.UserInfoUtil;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import java.util.Map;


@Listeners({TestListener.class})
public class BaseTest {

    private String reqBody = "{  \"code\": \"9M3DC\",  \"codeId\": \"1504664616195\",  \"passwd\": \"zd1234\",  \"userName\": \"zhengda\",  \"userType\": \"A\"}";

    @BeforeSuite
    public void before() throws Exception{

        initUserInfo();

        // 生成时间戳
        String epoch = Common.getEpoch();
        String url = Config.url + "/api/v1/auth/verifycode?timeStamp=" + String.valueOf(epoch);

        // 调用verify接口
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).execute();
        String verifyCode = Common.getVerifyCode(epoch);

        reqBody = reqBody.replace("1504664616195", String.valueOf(epoch)).replace("9M3DC", verifyCode);
//        String respBody = httpUtil.doPost("/api/v1//auth/login", "", reqBody);

        url = Config.url + "/api/v1//auth/login";
        if (!url.startsWith("http://")){
            url = "http://" + url;
        }

        MediaType mt = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(mt, reqBody);
        Request request2 = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();
        Response response2 = client.newCall(request2).execute();

        Map mapType = JSON.parseObject(response2.body().string(), Map.class);
        Config.token = (String) mapType.get("data");
        Config.epoch = String.valueOf(epoch);
        System.out.println("auth token is :Bearer  " + Config.token);
        createUserTest();


    }

//    @BeforeSuite
//    public void before() throws Exception{
//
//        initUserInfo();
//
//        // 生成时间戳
//        String epoch = Common.getEpoch();
//        String url = Config.url + "/api/v1/auth/verifycode?timeStamp=" + String.valueOf(epoch);
//
//        // 调用verify接口
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .build();
//
//        client.newCall(request).execute();
//        String verifyCode = Common.getVerifyCode(epoch);
//
//        reqBody = reqBody.replace("1504664616195", String.valueOf(epoch)).replace("9M3DC", verifyCode);
////        String respBody = httpUtil.doPost("/api/v1//auth/login", "", reqBody);
//
//        url = Config.url + "/api/v1//auth/login";
//        if (!url.startsWith("http://")){
//            url = "http://" + url;
//        }
//
//        MediaType mt = MediaType.parse("application/json; charset=utf-8");
//
//        RequestBody body = RequestBody.create(mt, reqBody);
//        Request request2 = new Request.Builder()
//                .url(url)
//                .post(body)
//                .addHeader("content-type", "application/json")
//                .build();
//        Response response2 = client.newCall(request2).execute();
//
//        Map mapType = JSON.parseObject(response2.body().string(), Map.class);
//        Config.token = (String) mapType.get("data");
//        Config.epoch = String.valueOf(epoch);
//        System.out.println("auth token is :Bearer  " + Config.token);
//
//    }



    private String userName = UserInfoUtil.getUserName();
    private String realName = UserInfoUtil.getRealName();
    private String mobile = UserInfoUtil.getMobile();
    private String email = UserInfoUtil.getEmailAddress();
    private int userId;
    private HttpUtil httpUtil = new HttpUtil();

    public void createUserTest() throws Exception{

        String reqBody = "{\"username\":\"$username\",\"realName\":\"$realname\",\"email\":\"$email\",\"mobile\":\"$mobile\"}";

        reqBody = reqBody.replace("$username", userName).
                replace("$realname", realName).
                replace("$mobile", mobile).
                replace("$email", email);

        String respBody = httpUtil.doPost("/api/v1/auth", "", reqBody);
        System.out.println(respBody);
        Map mapType = JSON.parseObject(respBody,Map.class);
        int code = (Integer)mapType.get("code");
        Assert.assertEquals(code , 0);

        // 设置新建用户的userId
        Map mapType2 = (Map)mapType.get("data");
        userId = (Integer) mapType2.get("userId");
        Config.userId = (Integer) mapType2.get("userId");

    }


    /**
     * 初始化用户信息
     */

    private void initUserInfo(){
        Config.userName = UserInfoUtil.getUserName();
        Config.realName = UserInfoUtil.getRealName();
        Config.mobile = UserInfoUtil.getMobile();
        Config.email = UserInfoUtil.getEmailAddress();
    }

}