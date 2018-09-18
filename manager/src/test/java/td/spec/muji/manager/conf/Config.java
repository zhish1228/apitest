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

  // 测试环境
  public static String environment = System.getProperty("environment");

  // db config
  private static SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlsessionfactory("mybatis-config.xml");
  public static SqlSession sqlSession = sqlSessionFactory.openSession();

  static {
    sqlSession.getConfiguration().addMappers("td.spec.muji.manager.dao");

    if (environment == null) {
      environment = "dev";
    }
  }
}
