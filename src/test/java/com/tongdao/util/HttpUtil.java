package com.tongdao.util;

import com.tongdao.conf.Config;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by zhengda on 2017/7/26.
 */
public class HttpUtil {

  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  /**
   * 发送geeeeet请求
   */
  public static String doGet(String path, String params) throws Exception {

    OkHttpClient client = new OkHttpClient();
    String url = Config.url + path;
    if (!url.startsWith("http://")) {
      url = "http://" + url;
    }

    if (params != null && params.length() != 0) {
      url = url + "?" + params;
    }

    Request request = new Request.Builder()
        .url(url)
        .get()
        .addHeader("Authorization", "Bearer " + Config.token)
        .build();

    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      String respBody = response.body().string();
      return respBody;

    } else {
      throw new IOException("Unexpected code " + response);
    }
  }

  /**
   * 发送geeeeeeeeet请求，不携带认证信息
   */
  public static String doGetWithoutToken(String path, String params) throws Exception {

    OkHttpClient client = new OkHttpClient();
    String url = Config.url + path;
    if (!url.startsWith("http://")) {
      url = "http://" + url;
    }

    if (params != null && params.length() != 0) {
      url = url + "?" + params;
    }

    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();

    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      return response.body().string();

    } else {
      throw new IOException("Unexpected code " + response);
    }
  }

  /**
   * 发送pooooost请求,不携带认证信息
   */
  public static String doPostWithoutToken(String path, String params, String json) throws Exception {

    OkHttpClient client = new OkHttpClient();
    String url = Config.url + path;
    if (!url.startsWith("http://")) {
      url = "http://" + url;
    }

    if (params != null && params.length() != 0) {
      url = url + "?" + params;
    }

    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder()
        .url(url)
        .post(body)
        .addHeader("content-type", "application/json")
        .build();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      return response.body().string();
    } else {
      throw new IOException("Unexpected code " + response);
    }
  }

  public static String doPost(String path, String params, String json) throws Exception {

    OkHttpClient client = new OkHttpClient();
    String url = Config.url + path;
    if (!url.startsWith("http://")) {
      url = "http://" + url;
    }

    if (params != null && params.length() != 0) {
      url = url + "?" + params;
    }

    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder()
        .url(url)
        .post(body)
        .addHeader("content-type", "application/json")
        .addHeader("Authorization", "Bearer " + Config.token)
        .build();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      return response.body().string();
    } else {
      throw new IOException("Unexpected code " + response);
    }
  }

  public static String doPut(String path, String params, String json) throws Exception {

    OkHttpClient client = new OkHttpClient();
    String url = Config.url + path;
    if (!url.startsWith("http://")) {
      url = "http://" + url;
    }

    if (params != null && params.length() != 0) {
      url = url + "?" + params;
    }

    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder()
        .url(url)
        .put(body)
        .addHeader("content-type", "application/json")
        .addHeader("Authorization", "Bearer " + Config.token)
        .build();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      return response.body().string();
    } else {
      throw new IOException("Unexpected code " + response);
    }
  }
}
