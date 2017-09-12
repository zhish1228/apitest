package com.tongdao.utils;

import com.tongdao.conf.Config;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;


/**
 * Created by zhengda on 2017/7/26.
 */
public class HttpUtil {

    /**
     *
     * 发送geeeeet请求
     * @param params
     * @return
     * @throws Exception
     */

    public String doGet(String path , String params) throws Exception{

        OkHttpClient client = new OkHttpClient();
        String url = Config.url + path;
        if (!url.startsWith("http://")){
            url = "http://" + url;
        }

        if(params != null && params.length() != 0){
            url = url + "?" + params;
        }

        System.out.println("test url is :" + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization","Bearer " + Config.token)
                .build();


        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            String respBody = response.body().string();
            System.out.println("resp body is: " + respBody );
            return respBody;

        }else {
            throw new IOException("Unexpected code " + response);
        }

    }


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     *
     * 发送pooooost请求
     * @param params
     * @param json
     * @return
     * @throws Exception
     */

    public String doPost(String path, String params, String json) throws Exception{

        OkHttpClient client = new OkHttpClient();
        String url = Config.url + path;
        if (!url.startsWith("http://")){
            url = "http://" + url;
        }

        if(params != null && params.length() != 0){
            url = url + "?" + params;
        }

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("Authorization","Bearer " + Config.token)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }


    public String doPut(String path, String params, String json) throws Exception{

        OkHttpClient client = new OkHttpClient();
        String url = Config.url + path;
        if (!url.startsWith("http://")){
            url = "http://" + url;
        }

        if(params != null && params.length() != 0){
            url = url + "?" + params;
        }

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("content-type", "application/json")
                .addHeader("Authorization","Bearer " + Config.token)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    /**
     *
     * query param为map时使用
     * @param url
     * @param paramsMap
     * @return
     * @throws Exception
     */

    private String spellUrl(String url, Map<String, String > paramsMap) throws Exception{

        StringBuilder tempParams = new StringBuilder();

        for (String key : paramsMap.keySet()) {
            tempParams.append("&");
            //对参数进行URLEncoder
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
        }
        url += "?" + tempParams.substring(1, tempParams.length());
        return url;
    }

}
