package com.imooc.canvas.service;

import com.imooc.canvas.common.MyBatisUtils;
import com.imooc.canvas.entity.Canvas;
import com.imooc.canvas.mapper.CanvasMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;


/**
 * 油画
 */
public class CanvasService {

    /**
     * 根据分类分页查询油画
     * @param categoryId    油画分类ID
     * @param page  要查询的页数
     * @param size  要查询的记录数
     * @return  油画集合
     */
    public List<Canvas> getCanvassByCategoryId(Long categoryId, Integer page, Integer size) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            //获取CanvasMapper的映射文件
            CanvasMapper mapper = sqlSession.getMapper(CanvasMapper.class);
            //通过mapper中的getCanvassByCategoryId方法可以获取油画集合
            return mapper.getCanvasByCategoryId(categoryId, (page - 1) * size, size);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 新增油画
     * @param canvas  油画信息
     */
    public void addCanvas(Canvas canvas,String creator) {
        //创建时间类
        Date now = new Date();
        //设置Canvas的创建时间
        canvas.setCreateTime(now);
        //设置Canvas的更新时间
        canvas.setUpdateTime(now);
        //记录Canvas的创建者
        canvas.setCreator(creator);
        canvas.setId(1L);
        //获取SqlSession对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            //获取CanvasMapper的映射文件
            CanvasMapper mapper = sqlSession.getMapper(CanvasMapper.class);
            //保存油画信息
            mapper.addCanvas(canvas);
            //提交当前sql会话
            sqlSession.commit();
        } finally {
            //关闭sql会话
            sqlSession.close();
        }
    }

    /**
     * 统计给定分类ID下的油画数量
     * @param categoryId    分类ID
     * @return  统计结果
     */
    public int countCanvassByCategoryId(Long categoryId) {
        //获取SqlSession对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            //获取CanvasMapper的映射文件
            CanvasMapper mapper = sqlSession.getMapper(CanvasMapper.class);
            //通过countCanvassByCategoryId方法可以获得当前分类下油画的数量
            return mapper.countCanvassByCategoryId(categoryId);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据ID查询对应的图片
     * @param id    油画ID
     * @return  只包含图片信息的油画实体
     */
    public Canvas getCanvasImg(Long id) {
        //获取SqlSession对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            //获取CanvasMapper的映射文件
            CanvasMapper mapper = sqlSession.getMapper(CanvasMapper.class);
            //通过getImg方法可以获得只包含图片的油画实体类
            return mapper.getImg(id);
        } finally {

            sqlSession.close();
        }
    }

    /**
     * 根据ID删除对应的油画
     * @param id 油画的ID
     */
    public void canvasDelete(Long id){
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            //获取CanvasMapper的映射文件
            CanvasMapper mapper = sqlSession.getMapper(CanvasMapper.class);

            mapper.deleteById(id);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 根据ID 查询油画信息
     * @param id 油画的id
     * @return 油画实体类
     */
    public Canvas canvasSelect(Long id){
        SqlSession sqlSession=MyBatisUtils.openSession();
        try {
            //获取CanvasMapper的映射文件
            CanvasMapper mapper=sqlSession.getMapper(CanvasMapper.class);

            return mapper.getCanvasById(id);
        }finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 修改油画信息
     * @param canvas 油画实体类
     */
    public  void canvasUpdate(Canvas canvas){
        SqlSession sqlSession=MyBatisUtils.openSession();
        try {
            //创建时间类
            Date now = new Date();
            //设置Canvas的更新时间
            canvas.setUpdateTime(now);
            //获取CanvasMapper的映射文件
            CanvasMapper mapper=sqlSession.getMapper(CanvasMapper.class);

            mapper.alterCanvas(canvas);

        }finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }




}
