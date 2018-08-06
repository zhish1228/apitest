package com.tongdao.dao;

import com.tongdao.entity.HttpParamEntity;

import java.util.List;

/**
 * Created by zhengda on 2017/9/6.
 */
public interface HttpParamDao {

  /**
   * 查测试数据
   */
  List<HttpParamEntity> selectByCaseName(String caseName);

}
