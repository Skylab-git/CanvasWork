package com.imooc.canvas.common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * MyBatis工具类
 */
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static {
        try {
            //初始化mybatis配置环境
            String resource = "config.xml";
            //读取xml  将xml变成数据流
            reader =  Resources.getResourceAsReader(resource);
            //用SqlSessionFactoryBuilder 传入xml数据创建 SqlSessionFactory工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //打开和数据库之间的会话
    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }
}
