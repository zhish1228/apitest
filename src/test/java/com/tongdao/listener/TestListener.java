package com.tongdao.listener;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter{
    private int m_count = 0;
     
    @Override
    public void onTestFailure(ITestResult tr) {

//        System.out.println(tr.getMethod());
//        System.out.println(tr.getTestClass());
        System.out.println(tr.getName() +  " ----- Test Failed");
    }
     
    @Override
    public void onTestSkipped(ITestResult tr) {
        System.out.println(tr.getName() +  " ----- Test Skiped");
    }
     
    @Override
    public void onTestSuccess(ITestResult tr) {

        System.out.println(tr.getName() +  " ----- Test Success");
    }

}
