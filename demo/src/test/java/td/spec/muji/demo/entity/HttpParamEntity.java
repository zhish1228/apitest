package td.spec.muji.demo.entity;

import lombok.Data;

/**
 * Created by zhengda on 2017/9/6. 对应db中存储参数的table
 */
@Data
public class HttpParamEntity {

  private int id;
  private String url;
  private String caseName;
  private String host;
  private int port;
  private String path;
  private String method;
  private String requestParams;
  private String requestBody;
  private int responseCode;
  private String responseBody;
  private String description;

}
