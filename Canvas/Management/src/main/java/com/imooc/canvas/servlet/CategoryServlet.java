package com.imooc.canvas.servlet;


import com.imooc.canvas.entity.Category;
import com.imooc.canvas.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * 分类Servlet
 *
 * @version 1.0
 */
public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        categoryService = new CategoryService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/category/list.do".equals(req.getServletPath())) {
            //通过CategoryService的getCateegories方法可以获取全部的油画分类
            List<Category> categories = categoryService.getCateegories();
            //将categories存储在request作用域中
            req.setAttribute("categories", categories);
            //转发到category_list.jsp页面
            req.getRequestDispatcher("/WEB-INF/views/biz/category_list.jsp").forward(req, resp);
        } else if ("/category/addPrompt.do".equals(req.getServletPath())) {
            req.getRequestDispatcher("/WEB-INF/views/biz/add_category.jsp").forward(req, resp);
        }
//        else if ("/category/add.do".equals(req.getServletPath())) {
//            //获取request请求中的name
//            String name = req.getParameter("name");
//            //创建分类对象
//            Category category = new Category();
//            //设置分类对象的名称
//            category.setName(name);
//            //然后将该分类对象添加到数据库的分类信息中
//            categoryService.addCategory(category);
//            req.getRequestDispatcher("/category/list.do").forward(req, resp);
//        }
    }

    @Override
    public void destroy() {
        super.destroy();
        categoryService = null;
    }
}
