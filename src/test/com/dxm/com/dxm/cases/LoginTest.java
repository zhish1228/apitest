package com.dxm.com.dxm.cases;

import com.dxm.api.Login;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengda on 2017/7/26.
 */
public class LoginTest {

    @Test
    public void test1(){
        Login.visitBaidu();
        System.out.println(Login.code);
        if(true){
            System.out.print("xxxxx");
        }else {
            System.out.print("xxxxx");

        }

//        Login.visitHao123();
//        System.out.println(Login.code);
//
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("nid", "7382932372654880386");
//        map.put("n_type", "0");
//        map.put("p_from", "1");
//        map.put("dtype", "-1");
//        Login.visitNews(map);
//        System.out.println(Login.code);
//        System.out.println(Login.body);

    }


}
