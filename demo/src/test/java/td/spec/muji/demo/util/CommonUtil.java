package td.spec.muji.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

import org.testng.annotations.Test;

import java.util.Map;

import redis.clients.jedis.Jedis;
import td.spec.muji.demo.conf.Config;

/**
 * Created by zhengda on 2017/9/6.
 */
@Slf4j
public class CommonUtil {

  /**
   * 获取linux时间戳
   */
  public static String getEpoch() {
    return String.valueOf(System.currentTimeMillis());
  }

  /**
   * 通过时间戳获取redis中存储的验证码
   */
  public static String getVerifyCode(String epoch) {
    Jedis jedis = new Jedis(Config.redisHost);
    String verifyCode = jedis.get(epoch);
    jedis.close();
    return verifyCode;
  }

  /**
   * 查看swagger提供的接口信息，测试使用
   */
  @Test
  public void showInterface() throws Exception {
    String s = HttpUtil.doGet("/v2/api-docs", "");
    Map mapType = JSON.parseObject(s, Map.class);
    JSONObject jsonObject = (JSONObject) mapType.get("paths");
    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
      log.info(entry.getKey());
    }
    log.info(String.valueOf(jsonObject.size()));
  }
}

