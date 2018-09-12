package td.spec.muji.manager.conf;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import td.spec.muji.manager.util.MyBatisUtil;

/**
 * Created by zhengda on 2017/9/5.
 */
public class Config {

  // 被测试服务端地址
  public static String url;

  public static String environment = System.getProperty("environment");
//  public static String environment = "dev";

  // db config
  private static SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlsessionfactory("mybatis-config.xml");
  public static SqlSession sqlSession = sqlSessionFactory.openSession();

  static {
    sqlSession.getConfiguration().addMappers("td.spec.muji.manager.dao");
  }

}
