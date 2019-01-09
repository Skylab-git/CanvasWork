package com.imooc.canvas.mapper;

import com.imooc.canvas.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 分类
 */
public interface CategoryMapper {

    /**
     * 查询全部油画分类
     * @return  全部油画分类
     */
    @Select("select id, name, createTime createTime, updateTime updateTime from category")
    List<Category> getCategories();




}
