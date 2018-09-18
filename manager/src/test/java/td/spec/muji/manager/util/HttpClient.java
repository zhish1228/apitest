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
public class HttpClient {

  private final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");

  /**
   * 发送geeeeet请求
   */
  public String doGet(String path, String params) throws Exception {

    HttpUrl url = getUrl(path, params);

    Request request = new Request.Builder()
        .url(url)
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
  public String doPost(String path, String params, String json) throws Exception {

    HttpUrl url = getUrl(path, params);
    RequestBody body = RequestBody.create(APPLICATION_JSON, json);
    Request request = new Request.Builder()
        .url(url)
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
  public String doPut(String path, String params, String json) throws Exception {

    HttpUrl url = getUrl(path, params);
    RequestBody body = RequestBody.create(APPLICATION_JSON, json);
    Request request = new Request.Builder()
        .url(url)
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
   * @param path   请求路径
   * @param params 请求参数
   * @return 请求的完整url
   */
  private HttpUrl getUrl(String path, String params) {

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
    return builder.build();
  }

}
