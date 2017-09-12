package com.tongdao.dao;

import com.tongdao.bean.HttpParam;

import java.util.List;

/**
 * Created by zhengda on 2017/9/6.
 */
public interface HttpParamDao {

    List<HttpParam> selectByCaseName(String caseName);

}
