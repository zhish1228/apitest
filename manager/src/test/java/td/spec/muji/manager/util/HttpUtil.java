package td.spec.muji.manager.util;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import td.spec.muji.manager.conf.Config;


/**
 * 接口测试如果以多线程方式执行会有问题.
 *
 * Created by zhengda on 2017/7/26.
 */
@Deprecated
public class HttpUtil {

  private static final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");

  /**
   * 发送geeeeet请求
   */
  public static String doGet(String path, String params) throws Exception {

    OkHttpClient client = new OkHttpClient();
    HttpUrl.Builder builder = HttpUrl.parse(Config.url).newBuilder();

    // set path
    String[] pathSplit = path.split("/");
    for (String p : pathSplit) {
      builder.addPathSegment(p);
    }

    // set parameter
    String[] paramSplit = params.split("&");
    for (String param : paramSplit) {
      String[] split = param.split("=");
      builder.addQueryParameter(split[0], split[1]);
    }

    HttpUrl url = builder.build();
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

  public static String doPost(String path, String params, String json) throws Exception {

    OkHttpClient client = new OkHttpClient();
    String url = Config.url + path;
    if (!url.startsWith("http://")) {
      url = "http://" + url;
    }

    if (params != null && params.length() != 0) {
      url = url + "?" + params;
    }

    RequestBody body = RequestBody.create(APPLICATION_JSON, json);
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

  public static String doPut(String path, String params, String json) throws Exception {

    OkHttpClient client = new OkHttpClient();
    String url = Config.url + path;
    if (!url.startsWith("http://")) {
      url = "http://" + url;
    }

    if (params != null && params.length() != 0) {
      url = url + "?" + params;
    }

    RequestBody body = RequestBody.create(APPLICATION_JSON, json);
    Request request = new Request.Builder()
        .url(url)
        .put(body)
        .addHeader("content-type", "application/json")
        .build();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      return response.body().string();
    } else {
      throw new IOException("Unexpected code " + response);
    }
  }
}
