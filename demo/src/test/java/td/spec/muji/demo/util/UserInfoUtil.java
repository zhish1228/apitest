package td.spec.muji.demo.util;


import java.io.UnsupportedEncodingException;
import java.util.Random;

import td.spec.muji.demo.conf.Config;

/**
 * Created by zhengda on 2017/9/11.
 */
public class UserInfoUtil {

  /**
   * 生成四位长度汉字
   */
  public static String getRealName() {

    char[] ch = new char[3];
    for (int i = 0; i < ch.length; i++) {
      ch[i] = getRandomChar();
    }
    return String.valueOf(ch) + Config.nameSuffix;
  }

  /**
   * 拼接字符串 随机生成用户名称
   */
  public static String getUserName() { //length表示生成字符串的长度
    int length = 8;
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

  public static String getEmailAddress() {

    String s = getUserName();
    return s + "@" + s + ".com";
  }

  /**
   * 生成手机号
   */
  public static String getMobile() {
    String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    int index = getNum(0, telFirst.length - 1);
    String first = telFirst[index];
    String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
    String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
    return first + second + third;
  }

  private static int getNum(int start, int end) {
    return (int) (Math.random() * (end - start + 1) + start);
  }

  /**
   * 随机生成常见的汉字
   */
  private static char getRandomChar() {
    String str = "";
    int hightPos; //
    int lowPos;

    Random random = new Random();

    hightPos = (176 + Math.abs(random.nextInt(39)));
    lowPos = (161 + Math.abs(random.nextInt(93)));

    byte[] b = new byte[2];
    b[0] = (Integer.valueOf(hightPos)).byteValue();
    b[1] = (Integer.valueOf(lowPos)).byteValue();

    try {
      str = new String(b, "GBK");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return str.charAt(0);
  }
}
