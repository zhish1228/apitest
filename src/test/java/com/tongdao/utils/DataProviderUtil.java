package com.tongdao.utils;


import com.tongdao.bean.HttpParam;
import com.tongdao.dao.HttpParamDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhengda on 2017/9/5.
 */
public class DataProviderUtil {

    private  static SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlsessionfactory();


    /**
     *
     * @param method
     * @return
     */
    @DataProvider(name = "httpParamsDataProvider")
    public static Iterator<Object[]> HttpParamsDataProvider(Method method){

        String methodName = method.getName();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HttpParamDao mapper = sqlSession.getMapper(HttpParamDao.class);
        List<HttpParam> httpParam =mapper.selectByCaseName(methodName);
        List<Object[]> data = new ArrayList<Object[]>();
        for(HttpParam h : httpParam){
            data.add(new Object[]{h});
        }

        return data.iterator();
    }


    @DataProvider(name = "globalConfDataProvider")
    public Object[][] GlobalConfDataProvider(Method method){

        Object[][] result = null;
        String methodName = method.getName();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        result = new Object[][]{new Object[]{"1","2","3"}, new Object[]{"4","5","6"}};
        return result;
    }
}
