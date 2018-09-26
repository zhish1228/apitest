package td.spec.muji.manager.cases;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import td.spec.muji.manager.entity.HttpParamEntity;
import td.spec.muji.manager.util.DataProviderUtil;

@Slf4j
public class ReflectTest extends BaseTest {

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void getCorrelators(HttpParamEntity httpParamEntity) throws Exception {

    log.info("test data is :" + httpParamEntity.toString());

    //  get 、post 、 delete 、 put ..
    String requestMethod = httpParamEntity.getRequestMethod().getName();
    String requestPath = httpParamEntity.getPath();
    String requestParams = httpParamEntity.getRequestParams();
    String requestBody = httpParamEntity.getRequestBody();
    String jsonPath = httpParamEntity.getJsonPath();

    Class<?> httpUtilClass = Class.forName("td.spec.muji.manager.util.HttpClient");
    Method method = httpUtilClass.getMethod("do" + requestMethod, String.class, String.class, String.class);

    // 反射
    Object responseBody = method.invoke(null, requestPath, requestParams, requestBody);

    // jsonpath断言
    JSONObject jsonObject = JSONObject.parseObject(responseBody.toString());
    Object eval = JSONPath.eval(jsonObject, jsonPath);
    Assert.assertEquals(eval.toString(), httpParamEntity.getExceptValue());

  }
}
