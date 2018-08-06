package com.tongdao.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtil {

  /**
   * 根据xml配置获取SqlSessionFactory
   *
   * @param resource mybatis配置文件路径
   * @return sf
   */
  public static SqlSessionFactory getSqlsessionfactory(String resource) {
    Reader reader = null;
    try {
      reader = Resources.getResourceAsReader(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new SqlSessionFactoryBuilder().build(reader);
  }
}
