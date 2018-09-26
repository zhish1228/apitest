package td.spec.muji.demo.conf.test;

import lombok.extern.slf4j.Slf4j;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhish1228 on 2018/8/6.
 */
@Slf4j
public class RetryAnalyzer implements IRetryAnalyzer {

  private AtomicInteger retryCount = new AtomicInteger(3);

  private boolean isRetryAvailable() {
    return (retryCount.intValue() > 0);
  }

  @Override
  public boolean retry(ITestResult result) {
    if (isRetryAvailable()) {
      log.error("重试:" + result.getMethod().getQualifiedName());
      retryCount.decrementAndGet();
      return true;
    }
    return false;
  }
}
