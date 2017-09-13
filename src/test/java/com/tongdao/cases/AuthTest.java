package com.tongdao.cases;

import com.alibaba.fastjson.JSON;
import com.tongdao.bean.HttpParam;
import com.tongdao.conf.Config;
import com.tongdao.utils.CommonUtil;
import com.tongdao.utils.DataProviderUtil;
import com.tongdao.utils.HttpUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class AuthTest extends BaseTest{

    private HttpUtil httpUtil = new HttpUtil();

    @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void getVerifyCodeTest(HttpParam httpParam) throws Exception{

        String epoch = CommonUtil.getEpoch();
        String params = "timeStamp=" + epoch;
        String s = httpUtil.doGet(httpParam.getPath(), params);
    }

    @Test(enabled = false, dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void createUserTest(HttpParam httpParam) throws Exception{
        String reqBody = httpParam.getRequestBody();
        reqBody = reqBody.replace("$username", Config.userName).
                          replace("$realname", Config.realName).
                          replace("$mobile", Config.mobile).
                          replace("$email", Config.email);

        String respBody = httpUtil.doPost(httpParam.getPath(), httpParam.getRequestParams(), reqBody);
        Map mapType = JSON.parseObject(respBody,Map.class);
        int code = (Integer)mapType.get("code");
        Assert.assertEquals(code , 0);

    }


    @Test(enabled = false,dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void loginTest(HttpParam httpParam) throws Exception{

    String epoch = CommonUtil.getEpoch();
    String url = "/api/v1/auth/verifycode?timeStamp=" + String.valueOf(epoch);
    httpUtil.doGet(url,  "");

    String verifyCode = CommonUtil.getVerifyCode(epoch);
    String reqBody = httpParam.getRequestBody().replace("$userName", Config.adminName)
            .replace("$passwd", Config.adminPassword).replace("$codeId", String.valueOf(epoch))
            .replace("$code", verifyCode);

    String respBody = httpUtil.doPost(httpParam.getPath(), "", reqBody);
    Map mapType = JSON.parseObject(respBody,Map.class);
    int code = (Integer)mapType.get("code");
    Assert.assertEquals(code , 0);

    }

}
