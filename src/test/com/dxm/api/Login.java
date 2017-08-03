package com.dxm.api;

import com.dxm.utils.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zhengda on 2017/7/26.
 */
public class Login extends HttpUtil{

    public static void visitBaidu(){

//        url = "http://www.baidu.com";
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tn", "sitehao123"));
        get();
    }

    public static void visitHao123(){
        url = "http://www.hao123.com";
        params = new ArrayList<NameValuePair>();
        get();
    }

    public static void visitNews(Map<String, String> m){
        url = "http://www.baidu.com/home/news/data/newspage";
        params = new ArrayList<NameValuePair>();

        for(Map.Entry entry : m.entrySet()){
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            params.add(new BasicNameValuePair(key, val));

        }
        get();

    }
}
