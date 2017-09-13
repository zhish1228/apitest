package com.tongdao.cases;

import com.alibaba.fastjson.JSON;
import com.tongdao.conf.Config;
import com.tongdao.listener.TestListener;
import com.tongdao.utils.CommonUtil;
import com.tongdao.utils.HttpUtil;
import com.tongdao.utils.UserInfoUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import java.util.Map;


@Listeners({TestListener.class})
public class BaseTest {

    private String reqBody = "{  \"code\": \"9M3DC\",  \"codeId\": \"1504664616195\",  \"passwd\": \"zd1234\",  \"userName\": \"zhengda\",  \"userType\": \"A\"}";
    private HttpUtil httpUtil = new HttpUtil();

    @BeforeSuite
    public void before() throws Exception{

        // 生成基本用户，创建用户时使用
        initUserInfo();

        // 生成时间戳
        String epoch = CommonUtil.getEpoch();

        // 获取验证码
        String requestParam = "timeStamp=" + epoch;
        httpUtil.doGetWithoutToken("/api/v1/auth/verifycode", requestParam);
        String verifyCode = CommonUtil.getVerifyCode(epoch);

        // 登录获取token
        reqBody = reqBody.replace("1504664616195", String.valueOf(epoch)).replace("9M3DC", verifyCode);
        String respBody = httpUtil.doPostWithoutToken("/api/v1/auth/login", "", reqBody);

        Map mapType = JSON.parseObject(respBody, Map.class);
        Config.token = (String) mapType.get("data");
        Config.epoch = String.valueOf(epoch);
        createUser();

    }

    private void cleanTestAccount(){



    }




    private void createUser() throws Exception{

        String reqBody = "{\"username\":\"$username\",\"realName\":\"$realname\",\"email\":\"$email\",\"mobile\":\"$mobile\"}";

        reqBody = reqBody.replace("$username", Config.userName).
                replace("$realname", Config.realName).
                replace("$mobile", Config.mobile).
                replace("$email", Config.email);

        String respBody = httpUtil.doPost("/api/v1/auth", "", reqBody);
        Map mapType = JSON.parseObject(respBody,Map.class);
        int code = (Integer)mapType.get("code");
        Assert.assertEquals(code , 0);

        // 设置新建用户的userId
        Map mapType2 = (Map)mapType.get("data");
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