package com.dxm.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.net.URI;
import java.util.List;

/**
 * Created by zhengda on 2017/7/26.
 */
public class HttpUtil {

    private static HttpClient httpClient = new DefaultHttpClient();
    protected static String url = "http://www.baidu.com";
    public static String body;
    public static int code;
    protected static List<NameValuePair> params;

    public static void get(){
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);
            // 设置参数
            if(params.size()!=0){
                String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
                httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            }
            // 发送请求
            HttpResponse httpresponse = httpClient.execute(httpget);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            code = httpresponse.getStatusLine().getStatusCode();
            body = EntityUtils.toString(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
