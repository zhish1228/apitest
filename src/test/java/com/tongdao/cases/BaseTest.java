package com.tongdao.cases;

import com.alibaba.fastjson.JSON;
import com.tongdao.conf.Config;
import com.tongdao.conf.test.RetryAnalyzer;
import com.tongdao.conf.test.TestListener;
import com.tongdao.util.CommonUtil;
import com.tongdao.util.HttpUtil;
import com.tongdao.util.UserInfoUtil;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.util.Map;

/**
 * 在beforesuite中可以做一些初始化工作，数据、用户等
 */
@Listeners({TestListener.class})
public class BaseTest {

  private String reqBody = "{  \"code\": \"9M3DC\",  \"codeId\": \"1504664616195\",  \"passwd\": \"zd1234\",  \"userName\": \"zhengda\",  \"userType\": \"A\"}";

  /**
   * 初始化环境
   */
  @BeforeSuite
  public void before(ITestContext context) throws Exception {

    // 全局添加重试监听器
    for (ITestNGMethod method : context.getAllTestMethods()) {
      method.setRetryAnalyzer(new RetryAnalyzer());
    }

    // 生成基本用户，创建用户时使用
    initUserInfo();

    // 生成时间戳
    String epoch = CommonUtil.getEpoch();

    // 获取验证码
    String requestParam = "timeStamp=" + epoch;
    HttpUtil.doGetWithoutToken("/api/v1/auth/verifycode", requestParam);
    String verifyCode = CommonUtil.getVerifyCode(epoch);

    // 登录获取token
    reqBody = reqBody.replace("1504664616195", String.valueOf(epoch)).replace("9M3DC", verifyCode);
    String respBody = HttpUtil.doPostWithoutToken("/api/v1/auth/login", "", reqBody);

    Map mapType = JSON.parseObject(respBody, Map.class);
    Config.token = (String) mapType.get("data");
    Config.epoch = String.valueOf(epoch);
    createUser();

  }

  private void createUser() throws Exception {

    String reqBody = "{\"username\":\"$username\",\"realName\":\"$realname\",\"email\":\"$email\",\"mobile\":\"$mobile\"}";

    reqBody = reqBody.replace("$username", Config.userName).
        replace("$realname", Config.realName).
        replace("$mobile", Config.mobile).
        replace("$email", Config.email);

    String respBody = HttpUtil.doPost("/api/v1/auth", "", reqBody);
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

    // 设置新建用户的userId
    Map mapType2 = (Map) mapType.get("data");
    Config.userId = (Integer) mapType2.get("userId");
  }


  /**
   * 初始化用户信息
   */

  private void initUserInfo() {
    Config.userName = UserInfoUtil.getUserName();
    Config.realName = UserInfoUtil.getRealName();
    Config.mobile = UserInfoUtil.getMobile();
    Config.email = UserInfoUtil.getEmailAddress();
  }

}