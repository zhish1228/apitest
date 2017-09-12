package com.tongdao.cases;

import com.tongdao.conf.Config;
import org.testng.annotations.Test;

/**
 * Created by zhengda on 2017/9/5.
 */
public class Te extends BaseTest{

    @Test(priority = 10)
    public void test1() throws Exception{

        Config.userId = 1;
    }

    @Test
    public void test2(){
       System.out.println(Config.userId);
    }




}
