package com.tongdao.cases;

import com.alibaba.fastjson.JSON;
import com.tongdao.conf.Config;
import com.tongdao.entity.HttpParamEntity;
import com.tongdao.util.DataProviderUtil;
import com.tongdao.util.HttpUtil;
import com.tongdao.util.UserInfoUtil;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;


public class UserTest extends BaseTest {

  /**
   * 用户冻结、激活；密码修改，重置，删除用户。
   */

  private String userName = UserInfoUtil.getUserName();
  private String realName = UserInfoUtil.getRealName();
  private String mobile = UserInfoUtil.getMobile();
  private String email = UserInfoUtil.getEmailAddress();
  private int userId;

  @BeforeClass()
  public void createUserTest() throws Exception {

    String reqBody = "{\"username\":\"$username\",\"realName\":\"$realname\",\"email\":\"$email\",\"mobile\":\"$mobile\"}";

    reqBody = reqBody.replace("$username", userName).
        replace("$realname", realName).
        replace("$mobile", mobile).
        replace("$email", email);

    String respBody = HttpUtil.doPost("/api/v1/auth", "", reqBody);
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

    // 设置新建用户的userId
    Map mapType2 = (Map) mapType.get("data");
    userId = (Integer) mapType2.get("userId");
  }

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void getCurrentUserTest(HttpParamEntity httpParam) throws Exception {

    String respBody = HttpUtil.doGet(httpParam.getPath(), "");
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void getUserListTest(HttpParamEntity httpParam) throws Exception {

    String respBody = HttpUtil.doGet(httpParam.getPath(), "");
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);
  }

  /**
   * 依赖@BeforeClass中的user Code
   */
  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void modifyUserTest(HttpParamEntity httpParam) throws Exception {

    String email = UserInfoUtil.getEmailAddress();
    String mobile = UserInfoUtil.getMobile();

    String reqBody = httpParam.getRequestBody().replace("$email", email).
        replace("$mobile", mobile).replace("$userId", String.valueOf(userId));
    String respBody = HttpUtil.doPost(httpParam.getPath(), httpParam.getRequestParams(), reqBody);

    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void enableUserTest(HttpParamEntity httpParam) throws Exception {

    String path = httpParam.getPath().replace("$userId", String.valueOf(userId));
    String respBody = HttpUtil.doPut(path, httpParam.getRequestParams(), httpParam.getRequestBody());
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }

  /**
   * 重置beforeClass中创建用户的密码
   */
  @Test(priority = 3, dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void resetPasswordTest(HttpParamEntity httpParam) throws Exception {

    String path = httpParam.getPath().replace("$userId", String.valueOf(userId));
    String respBody = HttpUtil.doPut(path, httpParam.getRequestParams(), httpParam.getRequestBody());
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }

  /**
   * 删除feforeClass用户的密码
   */
  @Test(priority = 4, dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void deleteUserTest(HttpParamEntity httpParam) throws Exception {

    String path = httpParam.getPath().replace("$userId", String.valueOf(Config.userId));
    String respBody = HttpUtil.doPut(path, httpParam.getRequestParams(), httpParam.getRequestBody());
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }


  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void getSingleUserTest(HttpParamEntity httpParam) throws Exception {

    String requestParam = httpParam.getRequestParams().replace("$userName", Config.userName).
        replace("$realName", Config.realName);
    String respBody = HttpUtil.doGet(httpParam.getPath(), requestParam);
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void changeResourcesTest(HttpParamEntity httpParam) throws Exception {

    String requestBody = httpParam.getRequestBody().replace("$userId", String.valueOf(Config.userId))
        .replace("@userName", Config.userName).replace("@realName", Config.realName)
        .replace("@email", Config.email);
    String respBody = HttpUtil.doPost(httpParam.getPath(), httpParam.getRequestParams(), requestBody);
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);
  }

  /**
   * 重置的管理员权限用户的用户密码
   */
  @Test(priority = 100, dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void changePasswordTest(HttpParamEntity httpParam) throws Exception {

    String requestBody = httpParam.getRequestBody().replace("$oldPassword", Config.adminPassword)
        .replace("$newPassword", Config.adminPassword)
        .replace("$renewPassword", Config.adminPassword);
    String respBody = HttpUtil.doPut(httpParam.getPath(), httpParam.getRequestParams(), requestBody);
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }
}
