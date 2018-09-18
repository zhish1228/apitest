package td.spec.muji.manager.cases;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import td.spec.muji.manager.entity.ManagerHttpParamEntity;
import td.spec.muji.manager.util.DataProviderUtil;

@Slf4j
public class ReflectTest extends BaseTest {

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void getCorrelators(ManagerHttpParamEntity managerHttpParamEntity) throws Exception {

    log.info("test data is :" + managerHttpParamEntity.toString());
    // 拼调用方法名称
    String requestMethod = managerHttpParamEntity.getRequestMethod();
    requestMethod = requestMethod.substring(0, 1).toUpperCase() + requestMethod.substring(1).toLowerCase();

    // 反射调用静态方法
    Class<?> httpUtilClass = Class.forName("td.spec.muji.manager.util.HttpUtil");
    Method method = httpUtilClass.getMethod("do" + requestMethod, String.class, String.class);
    Object responseBody = method.invoke(null, managerHttpParamEntity.getPath(), managerHttpParamEntity.getRequestParams());

    // 通过jsonpath断言
    JSONObject res = JSONObject.parseObject(responseBody.toString());
    Object eval = JSONPath.eval(res, "$.code");
    Assert.assertEquals(eval.toString(), managerHttpParamEntity.getExceptValue());

  }

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void getCorrelators2(ManagerHttpParamEntity managerHttpParamEntity) throws Exception {

    log.info("test data is :" + managerHttpParamEntity.toString());

    // http request
    String requestMethod = getRequestMethodName(managerHttpParamEntity.getRequestMethod());
    String requestPath = managerHttpParamEntity.getPath();
    String requestParam = managerHttpParamEntity.getRequestParams();

    // 反射调用静态方法
    Class<?> httpClientClazz = Class.forName("td.spec.muji.manager.util.HttpClient");
    Method method = httpClientClazz.getMethod("do" + requestMethod, String.class, String.class);
    Object responseBody = method.invoke(httpClientClazz.newInstance(), requestPath, requestParam);

    // 通过jsonpath断言
    JSONObject res = JSONObject.parseObject(responseBody.toString());
    Object eval = JSONPath.eval(res, "$.code");
    Assert.assertEquals(eval.toString(), managerHttpParamEntity.getExceptValue());
  }
}
