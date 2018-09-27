package td.spec.muji.manager.cases;

import lombok.extern.slf4j.Slf4j;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import td.spec.muji.manager.conf.Config;
import td.spec.muji.manager.conf.test.RetryAnalyzer;
import td.spec.muji.manager.conf.test.TestListener;
import td.spec.muji.manager.dao.ManagerHttpParamDao;
import td.spec.muji.manager.entity.ManagerConfigParamEntity;

/**
 * 在beforesuite中可以做一些初始化工作
 */
@Slf4j
@Listeners({TestListener.class})
public class BaseTest {


  /**
   * 初始化环境
   */
  @BeforeSuite
  public void before(ITestContext context) {

    // 全局添加重试监听器,不需要在xml中配置
    addRetryListener(context);
    // 设置测试地址
    initTestAddr();

    // 获取token
    // 生成当前时间戳,请求验证码地址(http://192.168.15.171:8009/api/v1/auth/verifycode?timeStamp=1537957093687),连接redis,通过时间戳获取value
    // doPost模拟登录，提供用户密码验证码
    // 解析respbody  赋值给Config.token

  }

  /**
   * 为所有方法添加重试监听
   *
   * @param context ctx
   */
  private void addRetryListener(ITestContext context) {

    for (ITestNGMethod method : context.getAllTestMethods()) {
      method.setRetryAnalyzer(new RetryAnalyzer());
    }
  }

  /**
   * 根据conf表中配置,初始化测试环境
   */
  private void initTestAddr() {

    ManagerHttpParamDao mapper = Config.sqlSession.getMapper(ManagerHttpParamDao.class);
    ManagerConfigParamEntity managerConfigParamEntity = mapper.selectByEnvironment(Config.environment);
    Config.url = "http://" + managerConfigParamEntity.getIp() + ":" + managerConfigParamEntity.getPort();
  }

  /**
   * 获取验证码,在测试环境用来跳过验证码识别,适用于redis保存验证码项目
   *
   * @param timeStamp 时间戳
   * @return 验证码
   */
  protected String getVerifyCode(String timeStamp) {

    String redisHost = "192.168.15.197:26379";
    String redisMasterName = "redis-master";

    Set<String> sentinels = new HashSet<>();
    sentinels.add(redisHost);

    // 哨兵模式
    JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(redisMasterName, sentinels);
    HostAndPort currentHostMaster = jedisSentinelPool.getCurrentHostMaster();

    log.info("current host is: " + currentHostMaster.getHost() + ":" + currentHostMaster.getPort());

    Jedis resource = jedisSentinelPool.getResource();
    resource.close();
    return resource.get(timeStamp);
  }

}