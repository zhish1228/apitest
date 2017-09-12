package com.tongdao.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtil {
  
    private final static SqlSessionFactory sqlSessionFactory;
      
    static{  
        String resource = "mybatis-config.xml";  
        Reader reader = null;
        try {  
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());  
        }  
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }
  
    public static SqlSessionFactory getSqlsessionfactory() {  
        return sqlSessionFactory;  
    }  
      
}  
