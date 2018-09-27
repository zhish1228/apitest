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

    // http请求方法
    String requestMethod = httpParamEntity.getRequestMethod().getName();
    // http请求路径
    String requestPath = httpParamEntity.getPath();
    // http请求参数
    String requestParams = httpParamEntity.getRequestParams();
    // http请求body
    String requestBody = httpParamEntity.getRequestBody();
    // 断言的jsonpath
    String jsonPath = httpParamEntity.getJsonPath();
    // 断言期望值
    String exceptValue = httpParamEntity.getExceptValue();

    // 执行http请求
    Class<?> httpUtilClass = Class.forName("td.spec.muji.manager.util.HttpUtil");
    Method method = httpUtilClass.getMethod(requestMethod, String.class, String.class, String.class);
    Object responseBody = method.invoke(null, requestPath, requestParams, requestBody);

    // 断言
    JSONObject jsonObject = JSONObject.parseObject(responseBody.toString());
    Object eval = JSONPath.eval(jsonObject, jsonPath);
    Assert.assertEquals(eval.toString(), exceptValue);

  }
}
