package com.tongdao.util;

import com.tongdao.dao.HttpParamDao;
import com.tongdao.entity.HttpParamEntity;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhengda on 2017/9/5.
 */
public class DataProviderUtil {

  private static SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlsessionfactory("mybatis-config.xml");

  /**
   * 从table中获取接口测试参数
   *
   * @param method 方法名。测试方法名需要与db中testcase字段对应，如果db中存在多个相同的testcase，测试脚本会执行两次
   * @return 测试参数的iterator
   */
  @DataProvider(name = "httpParamsDataProvider")
  public static Iterator<Object[]> HttpParamsDataProvider(Method method) {

    String methodName = method.getName();
    SqlSession sqlSession = sqlSessionFactory.openSession();

    HttpParamDao mapper = sqlSession.getMapper(HttpParamDao.class);
    List<HttpParamEntity> httpParam = mapper.selectByCaseName(methodName);
    List<Object[]> data = new ArrayList<Object[]>();
    for (HttpParamEntity h : httpParam) {
      data.add(new Object[]{h});
    }
    return data.iterator();
  }

}
