package td.spec.muji.manager.util;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import td.spec.muji.manager.conf.Config;
import td.spec.muji.manager.dao.ManagerHttpParamDao;
import td.spec.muji.manager.entity.HttpParamEntity;

/**
 * Created by zhish1228 on 2017/9/5.
 */
public class DataProviderUtil {

  /**
   * 从table中获取接口测试参数
   *
   * @param method 方法名。测试方法名需要与db中testcase字段对应，如果db中存在多个相同的testcase，测试脚本会执行两次
   * @return 测试参数的iterator
   */
  @DataProvider(name = "httpParamsDataProvider")
  public static Iterator<Object[]> HttpParamsDataProvider(Method method) {

    String methodName = method.getName();

    ManagerHttpParamDao mapper = Config.sqlSession.getMapper(ManagerHttpParamDao.class);
    List<HttpParamEntity> httpParam = mapper.selectByCaseName(methodName);
    List<Object[]> data = new ArrayList<Object[]>();
    for (HttpParamEntity entity : httpParam) {
      data.add(new Object[]{entity});
    }
    return data.iterator();
  }

}
