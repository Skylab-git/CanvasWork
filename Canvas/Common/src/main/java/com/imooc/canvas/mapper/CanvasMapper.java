package com.imooc.canvas.mapper;

import com.imooc.canvas.entity.Canvas;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 油画
 */
public interface CanvasMapper {
    /**
     * 分页查询油画
     *
     * @param skip 跳过的记录数
     * @param size 要查询的记录数
     * @return 油画集合
     */
    @Select("select * from canvas order by createTime desc limit #{skip},#{size}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "categoryId", property = "categoryId"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "createTime", property = "createTime"),
            @Result(column = "updateTime", property = "updateTime")
    })
    List<Canvas> getCanvas(@Param("skip") Integer skip, @Param("size") Integer size);

    /**
     * 根据分类分页查询油画
     *
     * @param categoryId 油画分类ID
     * @param skip       跳过的记录数
     * @param size       要查询的记录数
     * @return 油画集合
     */

    @Select("select id,categoryId,name, creator, price,smallImg, createTime, updateTime,description,details " +
            "from canvas where categoryId = #{categoryId} order by createTime desc limit #{skip}, #{size}")
    List<Canvas> getCanvasByCategoryId(@Param("categoryId") Long categoryId, @Param("skip") Integer skip, @Param("size") Integer size);
//    @Select("select id,categoryId,name,creator,price, createTime, updateTime,description,details" +
//            "from canvas where categoryId=#{categoryId} order by createTime desc limit #{skip},#{size}")
//    List<Canvas> getCanvasByCategoryId(@Param("categoryId") Long categoryId, @Param("skip") Integer skip, @Param("size") Integer size);

    /**
     * 根据分类ID进行油画数量的统计
     *
     * @param categoryId 分类ID
     * @return 分类下油画数量
     */
    @Select("select count(*) from Canvas where categoryId = #{categoryId}")
    int countCanvassByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 新增油画信息
     *
     * @param canvas 油画信息
     */
//    @Insert("insert into canvas(categoryId, name, creator, price, smallImg, createTime, updateTime,description) " +
//            "value (#{canvas.categoryId}, #{canvas.name}, #{canvas.creator}, #{canvas.price}, #{canvas.smallImg}, " +
//            "#{canvas.createTime}, #{canvas.updateTime}),#{canvas.description}")
    @Insert("insert into canvas(categoryId, name, creator, price,smallImg, createTime, updateTime,description) value (#{canvas.categoryId},#{canvas.name},#{canvas.creator},#{canvas.price},#{canvas.smallImg},#{canvas.createTime},#{canvas.updateTime},#{canvas.description});")
    void addCanvas(@Param("canvas") Canvas canvas);

    /**
     * 查询油画图片信息
     *
     * @param id 油画ID
     * @return 只包含图片的油画实体
     */
    @Select("select smallImg from canvas where id = #{id} for update")
    Canvas getImg(@Param("id") Long id);

    /**
     * 根据ID删除某一具体油画
     *
     * @param id 要删除的油画ID
     */
    @Delete("delete from canvas where id = #{id}")
    void deleteById(@Param("id") Long id);

    /**
     * 根据ID 查询油画信息
     *
     * @param id 油画ID
     * @return 油画实体类
     */
    @Select("SELECT s.name,s.id,y.name,s.price,s.smallImg,s.description FROM canvas AS s LEFT JOIN category AS y ON s.categoryId=y.id WHERE s.id=#{id}")
    Canvas getCanvasById(@Param("id") Long id);

    /**
     * 修改保存 油画信息
     *
     * @param canvas 油画实体类
     */

    @Update("UPDATE canvas SET categoryId=#{canvas.categoryId},name=#{canvas.name},price=#{canvas.price},description=#{canvas.description} WHERE id=#{canvas.id}")
    void alterCanvas(@Param("canvas") Canvas canvas);

}
