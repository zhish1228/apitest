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
 * Created by zhengda on 2017/7/26.
 */
public class HttpUtil {

  private static final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");

  /**
   * 发送geeeeet请求
   */
  public static String doGet(String requestPath, String requestParams, String requestBody) throws Exception {

    HttpUrl url = getUrl(requestPath, requestParams);
    Request request = new Request.Builder()
        .url(url)
//        .addHeader("Authorization", "Bearer " + Config.token)
        .get()
        .build();

    OkHttpClient client = new OkHttpClient();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      return response.body().string();
    } else {
      throw new IOException("Unexpected code " + response);
    }
  }

  /**
   * 发送posssssssssssst请求
   */
  public static String doPost(String requestPath, String requestParams, String requestBody) throws Exception {

    HttpUrl url = getUrl(requestPath, requestParams);
    RequestBody body = RequestBody.create(APPLICATION_JSON, requestBody);
    Request request = new Request.Builder()
        .url(url)
//        .addHeader("Authorization", "Bearer " + Config.token)
        .post(body)
        .addHeader("content-type", "application/json")
        .build();

    OkHttpClient client = new OkHttpClient();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      return response.body().string();
    } else {
      throw new IOException("Unexpected code " + response);
    }
  }

  /**
   * 发送puuuuuuuuuuuut请求
   */
  public static String doPut(String path, String params, String requestBody) throws Exception {

    HttpUrl url = getUrl(path, params);
    RequestBody body = RequestBody.create(APPLICATION_JSON, requestBody);
    Request request = new Request.Builder()
        .url(url)
//        .addHeader("Authorization", "Bearer " + Config.token)
        .put(body)
        .addHeader("content-type", "application/json")
        .build();

    OkHttpClient client = new OkHttpClient();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      return response.body().string();
    } else {
      throw new IOException("Unexpected code " + response);
    }
  }

  /**
   * 根据config中的url以及db中的path和param拼接url
   *
   * @param requestPath   请求路径 以斜线/分割
   * @param requestParams 请求参数 参数以&符号分割 KV以=分割
   * @return 请求的完整url
   */
  private static HttpUrl getUrl(String requestPath, String requestParams) {

    HttpUrl.Builder builder = HttpUrl.parse(Config.url).newBuilder();

    // set path
    String[] pathSplit = requestPath.split("/");
    for (String p : pathSplit) {
      builder.addPathSegment(p);
    }

    // set parameter
    String[] paramSplit = requestParams.split("&");
    for (String param : paramSplit) {
      String[] split = param.split("=");
      builder.addQueryParameter(split[0], split[1]);
    }
    return builder.build();
  }

}
