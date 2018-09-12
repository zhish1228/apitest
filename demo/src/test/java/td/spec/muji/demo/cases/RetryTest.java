package td.spec.muji.demo.cases;


import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import td.spec.muji.demo.conf.test.RetryAnalyzer;
import td.spec.muji.demo.conf.test.TestListener;

/**
 * Created by zhengda on 2018/8/6.
 */
@Slf4j
@Listeners({TestListener.class})
public class RetryTest {

  @BeforeSuite
  public void before(ITestContext context) {

    log.info("init te");
    // 全局添加重试监听器
    for (ITestNGMethod method : context.getAllTestMethods()) {
      method.setRetryAnalyzer(new RetryAnalyzer());
    }
  }

  @Test
  public void retryTest() {
    log.info("test for retry");
    Assert.fail();
  }

  @Test
  public void retryTest2() {
    log.info("test for retry2");
    Assert.fail();
  }
}
