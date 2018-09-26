package td.spec.muji.manager.entity;

import lombok.Data;

import td.spec.muji.manager.model.constants.RequestMethodEnum;

/**
 * Created by zhengda on 2017/9/6. 对应db中存储参数的table
 */
@Data
public class HttpParamEntity {

  private int id;
  private String caseName;
  private String path;
  private RequestMethodEnum requestMethod;
  private String requestParams;
  private String requestBody;
  private int responseCode;
  private String responseBody;
  private String jsonPath;
  private String exceptValue;
  private String description;

}

