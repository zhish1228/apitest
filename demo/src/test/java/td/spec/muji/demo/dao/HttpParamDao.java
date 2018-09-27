package td.spec.muji.demo.dao;


import java.util.List;

import td.spec.muji.demo.entity.HttpParamEntity;

/**
 * Created by zhish1228 on 2017/9/6.
 */
public interface HttpParamDao {

  /**
   * 查测试数据
   */
  List<HttpParamEntity> selectByCaseName(String caseName);

}
