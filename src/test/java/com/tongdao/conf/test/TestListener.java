package com.tongdao.conf.test;

import lombok.extern.slf4j.Slf4j;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 异常或者其他测试结果时可以做一些操作
 * 也可是实现另外个接口
 *
 * @see ITestListener
 */
@Slf4j
public class TestListener extends TestListenerAdapter {

  private String startTime;
  private String endTime;
  private String className;
  private String methodName;
  private float execTime;

  @Override
  public void onTestFailure(ITestResult tr) {

    startTime = stampToDate(tr.getStartMillis());
    endTime = stampToDate(tr.getEndMillis());
    execTime = execTime(tr);

    methodName = tr.getName();
    className = tr.getTestClass().getName();

    System.out.println("测试类:" + className + "  中测试方法:" + methodName + " 执行失败");
    System.out.println("测试开始时间:" + startTime + ".测试完成时间:" + endTime + ".测试耗时:" + execTime);
  }

  @Override
  public void onTestSkipped(ITestResult tr) {

    startTime = stampToDate(tr.getStartMillis());
    endTime = stampToDate(tr.getEndMillis());
    execTime = execTime(tr);

    methodName = tr.getName();
    className = tr.getTestClass().getName();

    System.out.println("测试类:" + className + "  中测试方法:" + methodName + " 跳过执行");
    System.out.println("测试开始时间:" + startTime + ".测试完成时间:" + endTime + ".测试耗时:" + execTime);

  }

  @Override
  public void onTestSuccess(ITestResult tr) {

    startTime = stampToDate(tr.getStartMillis());
    endTime = stampToDate(tr.getEndMillis());
    execTime = execTime(tr);

    methodName = tr.getName();
    className = tr.getTestClass().getName();

    System.out.println("测试类:" + className + "  中测试方法:" + methodName + " 执行成功");
    System.out.println("测试开始时间:" + startTime + ".测试完成时间:" + endTime + ".测试耗时:" + execTime);
  }


  private float execTime(ITestResult tr) {

    return (float) (tr.getEndMillis() - tr.getStartMillis()) / 1000;
  }

  private String stampToDate(long l) {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(l);
    res = simpleDateFormat.format(date);
    return res;
  }

}
