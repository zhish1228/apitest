package td.spec.muji.demo.cases;

import com.alibaba.fastjson.JSON;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import td.spec.muji.demo.entity.HttpParamEntity;
import td.spec.muji.demo.util.DataProviderUtil;
import td.spec.muji.demo.util.HttpUtil;

/**
 * Created by zhengda on 2017/9/11.
 */
public class InfoTest extends BaseTest {

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void getEnumInfoTest(HttpParamEntity httpParam) throws Exception {
    String respBody = HttpUtil.doGet(httpParam.getPath(), httpParam.getRequestParams());
    Map mapType = JSON.parseObject(respBody, Map.class);
    int code = (Integer) mapType.get("code");
    Assert.assertEquals(code, 0);
  }
}
