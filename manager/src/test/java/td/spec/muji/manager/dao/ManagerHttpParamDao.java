package td.spec.muji.manager.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import td.spec.muji.manager.entity.ManagerConfigParamEntity;
import td.spec.muji.manager.entity.ManagerHttpParamEntity;

/**
 * Created by zhengda on 2017/9/6.
 */
@Mapper
public interface ManagerHttpParamDao {

  /**
   * 查测试数据
   */
  @Select("select id as 'id', casename as 'casename', path as 'path', request_method as 'requestMethod', " +
      "request_params as 'requestParams', request_body as 'requestBody', response_code as responseCode, " +
      "response_body as 'responseBody', jsonpath as 'jsonpath', except_value as 'exceptValue' ,description as 'description' " +
      "from t_api_manager where casename = #{caseName}  and available = 1")
  List<ManagerHttpParamEntity> selectByCaseName(String caseName);

  /**
   * 查询初始化数据
   *
   * @param environment 环境类型 用来驱动不同环境的case
   */
  @Select("select * from t_api_config where environment = #{environment}")
  ManagerConfigParamEntity selectByEnvironment(String environment);

}
