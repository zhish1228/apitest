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

    // 全局添加重试监听器
    addRetryListener(context);
    // 设置测试地址
    initTestAddr();

  }

  private void addRetryListener(ITestContext context) {

    for (ITestNGMethod method : context.getAllTestMethods()) {
      method.setRetryAnalyzer(new RetryAnalyzer());
    }
  }

  private void initTestAddr() {

    ManagerHttpParamDao mapper = Config.sqlSession.getMapper(ManagerHttpParamDao.class);
    ManagerConfigParamEntity managerConfigParamEntity = mapper.selectByEnvironment(Config.environment);
    Config.url = "http://" + managerConfigParamEntity.getIp() + ":" + managerConfigParamEntity.getPort();
  }


  protected String getRequestMethodName(String methodStr) {

    // 拼调用方法名称
    return methodStr.substring(0, 1).toUpperCase() + methodStr.substring(1).toLowerCase();
  }

  /**
   * 获取验证码
   *
   * @param timeStamp 时间戳
   * @return 验证码
   */
  protected String getVerifyCode(String timeStamp) {

    String redisHost = "192.168.15.197:26379";
    String redisMasterName = "redis-master";

    Set<String> sentinels = new HashSet<>();
    sentinels.add(redisHost);

    JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(redisMasterName, sentinels);
    HostAndPort currentHostMaster = jedisSentinelPool.getCurrentHostMaster();

    log.info("current host is: " + currentHostMaster.getHost() + ":" + currentHostMaster.getPort());

    Jedis resource = jedisSentinelPool.getResource();
    resource.close();
    return resource.get(timeStamp);
  }
}