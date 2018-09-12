package td.spec.muji.manager.entity;

import lombok.Data;

/**
 * Created by zhengda on 2017/9/6. 对应db中存储参数的table
 */
@Data
public class ManagerHttpParamEntity {

  private int id;
  private String caseName;
  private String path;
  private String requestMethod;
  private String requestParams;
  private String requestBody;
  private int responseCode;
  private String responseBody;
  private String jsonPath;
  private String exceptValue;
  private String description;

}

