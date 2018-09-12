package td.spec.muji.manager.cases;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import td.spec.muji.manager.conf.Config;
import td.spec.muji.manager.conf.test.RetryAnalyzer;
import td.spec.muji.manager.conf.test.TestListener;
import td.spec.muji.manager.dao.ManagerHttpParamDao;
import td.spec.muji.manager.entity.ManagerConfigParamEntity;

/**
 * 在beforesuite中可以做一些初始化工作
 */
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
}