package com.imooc.canvas.service;

import com.imooc.canvas.common.MyBatisUtils;
import com.imooc.canvas.entity.Category;
import com.imooc.canvas.mapper.CategoryMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

/**
 * 分类
 */
public class CategoryService {
    /**
     * 查询全部油画分类
     * @return  全部油画分类
     */
    public List<Category> getCateegories() {
        //获取SqlSession对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            //获取canvasMapper的映射文件
            CategoryMapper mapper = sqlSession.getMapper(CategoryMapper.class);
            //通过getCategories()方法可以获得全部油画的分类
            return mapper.getCategories();
        } finally {
            sqlSession.close();
        }
    }


}
