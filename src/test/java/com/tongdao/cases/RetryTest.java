package com.tongdao.cases;

import com.tongdao.conf.test.RetryAnalyzer;
import com.tongdao.conf.test.TestListener;
import com.tongdao.entity.HttpParamEntity;
import com.tongdao.util.DataProviderUtil;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by zhengda on 2018/8/6.
 */
@Slf4j
@Listeners({TestListener.class})
public class RetryTest {

  @BeforeSuite
  public void before(ITestContext context) throws Exception {

    // 全局添加重试监听器
    for (ITestNGMethod method : context.getAllTestMethods()) {
      method.setRetryAnalyzer(new RetryAnalyzer());
    }
  }

  @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
  public void hehe(HttpParamEntity httpParam) throws Exception {

    String caseName = httpParam.getCaseName();
    log.info(caseName);
    Assert.fail();
  }
}
