package com.tongdao.cases;

import com.alibaba.fastjson.JSON;
import com.tongdao.bean.HttpParam;
import com.tongdao.conf.Config;
import com.tongdao.utils.Common;
import com.tongdao.utils.DataProviderUtil;
import com.tongdao.utils.HttpUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class AuthTest extends BaseTest{

    private final String getVerifyCodePath = "/api/v1/auth/verifycode";
    private final String loginPath = "/api/v1/auth/login";

    private HttpUtil httpUtil = new HttpUtil();


    @Test
    public void getVerifyCodeTest() throws Exception{

        String epoch = Common.getEpoch();
        String params = "timeStamp=" + epoch;
        httpUtil.doGet(getVerifyCodePath, params);
    }

    @Test(priority = 100, dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void createUserTest(HttpParam httpParam) throws Exception{
        String reqBody = httpParam.getRequestBody();
        reqBody = reqBody.replace("$username", Config.userName).
                          replace("$realname", Config.realName).
                          replace("$mobile", Config.mobile).
                          replace("$email", Config.email);

        String respBody = httpUtil.doPost(httpParam.getPath(), httpParam.getRequestParams(), reqBody);
        System.out.println(respBody);
        Map mapType = JSON.parseObject(respBody,Map.class);
        int code = (Integer)mapType.get("code");
        Assert.assertEquals(code , 0);

    }


    @Test
    public void loginTest() throws Exception{

    String reqBody = "{  \"code\": \"9M3DC\",  \"codeId\": \"1504664616195\",  \"passwd\": \"jt31m5\",  \"userName\": " +
            "\"test11\",  \"userType\": \"A\"}";
    String epoch = Common.getEpoch();
    String url = "/api/v1/auth/verifycode?timeStamp=" + String.valueOf(epoch);
    httpUtil.doGet(url,  "");

    String verifyCode = Common.getVerifyCode(epoch);
    reqBody = reqBody.replace("1504664616195", String.valueOf(epoch)).replace("9M3DC", verifyCode);

    String s= httpUtil.doPost(loginPath, "", reqBody);
    System.out.println(s);

    }

}
