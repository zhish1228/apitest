package td.spec.muji.demo.cases;

import com.alibaba.fastjson.JSON;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import td.spec.muji.demo.conf.Config;
import td.spec.muji.demo.entity.HttpParamEntity;
import td.spec.muji.demo.util.CommonUtil;
import td.spec.muji.demo.util.DataProviderUtil;
import td.spec.muji.demo.util.HttpUtil;

public class AuthTest extends BaseTest {

  @Test(dataProvider = "HttpParamDataProvider", dataProviderClass = DataProviderUtil.class)
  public void getVerifyCodeTest(HttpParamEntity HttpParamEntity) throws Exception {

    String epoch = CommonUtil.getEpoch();
    String params = "timeStamp=" + epoch;
    String s = HttpUtil.doGet(HttpParamEntity.getPath(), params);
  }

  @Test(enabled = false, dataProvider = "HttpParamDataProvider", dataProviderClass = DataProviderUtil.class)
  public void createUserTest(HttpParamEntity HttpParamEntity) throws Exception {
    String reqBody = HttpParamEntity.getRequestBody();
    reqBody = reqBody.replace("$username", Config.userName).
        replace("$realname", Config.realName).
        replace("$mobile", Config.mobile).
        replace("$email", Config.email);

    String respBody = HttpUtil.doPost(HttpParamEntity.getPath(), HttpParamEntity.getRequestParams(), reqBody);
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }


  @Test(enabled = false, dataProvider = "HttpParamDataProvider", dataProviderClass = DataProviderUtil.class)
  public void loginTest(HttpParamEntity HttpParamEntity) throws Exception {

    String epoch = CommonUtil.getEpoch();
    String url = "/api/v1/auth/verifycode?timeStamp=" + String.valueOf(epoch);
    HttpUtil.doGet(url, "");

    String verifyCode = CommonUtil.getVerifyCode(epoch);
    String reqBody = HttpParamEntity.getRequestBody().replace("$userName", Config.adminName)
        .replace("$passwd", Config.adminPassword).replace("$codeId", String.valueOf(epoch))
        .replace("$code", verifyCode);

    String respBody = HttpUtil.doPost(HttpParamEntity.getPath(), "", reqBody);
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);

  }

}
