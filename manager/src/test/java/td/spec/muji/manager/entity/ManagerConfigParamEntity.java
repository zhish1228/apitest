package td.spec.muji.manager.entity;

import lombok.Data;

/**
 * Created by zhengda on 2017/9/6. 对应db中存储参数的table
 */
@Data
public class ManagerConfigParamEntity {

  private int id;
  private String ip;
  private int port;
  private String environment;
  private String description;

}
