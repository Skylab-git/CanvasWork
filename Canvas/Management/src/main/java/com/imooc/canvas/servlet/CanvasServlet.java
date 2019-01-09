package com.imooc.canvas.servlet;

import com.imooc.canvas.entity.Canvas;
import com.imooc.canvas.entity.Category;
import com.imooc.canvas.service.CanvasService;
import com.imooc.canvas.service.CategoryService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CanvasServlet extends HttpServlet {

    private CanvasService canvasService;

    private CategoryService categoryService;
    //创建者
    private String creator;

    @Override
    public void init() throws ServletException {
        super.init();
        canvasService = new CanvasService();
        categoryService = new CategoryService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/canvas/list.do".equals(req.getServletPath())) {
            //获取油画分类的id
            String categoryIdStr = req.getParameter("categoryId");

            //定义一个长整型的油画分类ID
            Long catgoryId = 1L;
            //如果前台页面传过来的categoryId不为null
            if (null != categoryIdStr) {
                //就将值的引用赋值给categoryId，此处需要将String类型的变量转换为Long型
                catgoryId = Long.valueOf(categoryIdStr);
            }
            //通过CateService中的getCanvassByCategoryId方法可以获取指定分类下的Canvass集合
            List<Canvas> canvas = canvasService.getCanvassByCategoryId(catgoryId, 1, 5000);
            //将Canvass存储在request作用域中
            req.setAttribute("canvas", canvas);
            //通过CategoryService的getCateegories方法可以获取全部的油画分类
            List<Category> categories = categoryService.getCateegories();
            //将categories存储在request作用域中
            req.setAttribute("categories", categories);
            //转发到Canvas_list.jsp页面
            req.getRequestDispatcher("/WEB-INF/views/biz/canvas_list.jsp").forward(req, resp);

        } else if ("/canvas/addPrompt.do".equals(req.getServletPath())) {
            //通过categoryService的getCateegories方法可以获取全部的油画分类
            List<Category> categories = categoryService.getCateegories();
            //将categories存储在request作用域中
            req.setAttribute("categories", categories);
            //转发到add_Canvas.jsp页面
            req.getRequestDispatcher("/WEB-INF/views/biz/add_canvas.jsp").forward(req, resp);
        } else if ("/canvas/add.do".equals(req.getServletPath())) {
            req.setCharacterEncoding("utf-8");
            creator = (String) req.getSession().getAttribute("username");
            if (ServletFileUpload.isMultipartContent(req)) {
                try {
                    //创建一个磁盘文件项工厂对象
                    FileItemFactory factory = new DiskFileItemFactory();
                    //首先得到ServletFileUpload，是文件上传的核心组件。它能够将request中的每一个属性字段都封装成FileItem对象
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    //解析请求
                    List<FileItem> items = upload.parseRequest(req);
                    //获取items的迭代对象
                    Iterator<FileItem> ite = items.iterator();
                    //创建Canvas对象
                    Canvas canvas = new Canvas();
                    //开始迭代items集合
                    while (ite.hasNext()) {
                        //将获取的每条数据存储到item对象中
                        FileItem item = ite.next();
                        //信息是普通的格式
                        if (item.isFormField()) {
                            //获取每个item对象的名字
                            String fieldName = item.getFieldName();
                            //当fieldName为categoryId
                            if ("categoryId".equals(fieldName)) {
                                //设置Canvas对象中的categoryId值为request中的categoryId
                                canvas.setCategoryId(Long.valueOf(item.getString()));
                            } else if ("name".equals(fieldName)) {//当fieldName为name
                                //设置Canvas对象中的level值为request中的name,为了防止出现中文乱码，将name值进行了转码
                                canvas.setName(new String(item.getString().getBytes("iso8859-1"), "utf-8"));
                            } else if ("price".equals(fieldName)) {//当fieldName为price
                                //设置Canvas对象中的price值为request中的price
                                canvas.setPrice(Integer.valueOf(item.getString()));
                            } else if ("description".equals(fieldName)) {//当fieldName为description
                                //设置Canvas对象中的description值为request中的description
                                canvas.setDescription(new String(item.getString().getBytes("iso8859-1"), "utf-8"));
                            }
                        } else {//信息是文件格式
                            //设置Canvas的图片属性为request中的图片数据
                            canvas.setSmallImg(item.get());
                        }
                    }

                    canvasService.addCanvas(canvas, creator);
                    req.getRequestDispatcher("/canvas/list.do").forward(req, resp);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if ("/canvas/canvasDelete.do".equals(req.getServletPath())) {
            //获取油画的id
            String canvasId = req.getParameter("canvasId");
            Long id = Long.valueOf(canvasId);

            canvasService.canvasDelete(id);
            //转发到canvas_list.jsp页面
            req.getRequestDispatcher("/canvas/list.do").forward(req, resp);
        } else if ("/camvas/modificationPrompt.do".equals(req.getServletPath())) {
            //获取油画的id
            String canvasId = req.getParameter("canvasId");
            Long id = Long.valueOf(canvasId);
            //获得被编辑的油画信息
            Canvas canvas = new Canvas();
            canvas = canvasService.canvasSelect(id);
            //获取分类信息
            List<Category> categories = categoryService.getCateegories();
            req.setAttribute("canvas", canvas);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/WEB-INF/views/biz/update_canvas.jsp").forward(req, resp);
        } else if ("/canvas/updateCanvas.do".equals(req.getServletPath())) {
            req.setCharacterEncoding("utf-8");
            creator = (String) req.getSession().getAttribute("username");
            if (ServletFileUpload.isMultipartContent(req)) {
                try {
                    //创建一个磁盘文件项工厂对象
                    FileItemFactory factory = new DiskFileItemFactory();
                    //首先得到ServletFileUpload，是文件上传的核心组件。它能够将request中的每一个属性字段都封装成FileItem对象
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    //解析请求
                    List<FileItem> items = upload.parseRequest(req);
                    //获取items的迭代对象
                    Iterator<FileItem> ite = items.iterator();
                    //创建Canvas对象
                    Canvas canvas = new Canvas();
                    String canvasId = req.getParameter("canvasId");
                    //开始迭代items集合
                    while (ite.hasNext()) {
                        //将获取的每条数据存储到item对象中
                        FileItem item = ite.next();
                        //信息是普通的格式
                        if (item.isFormField()) {
                            //获取每个item对象的名字
                            String fieldName = item.getFieldName();
                            //当fieldName为categoryId
                            if ("categoryId".equals(fieldName)) {
                                //设置Canvas对象中的categoryId值为request中的categoryId
                                canvas.setCategoryId(Long.valueOf(item.getString()));
                            } else if ("name".equals(fieldName)) {//当fieldName为name
                                //设置Canvas对象中的level值为request中的name,为了防止出现中文乱码，将name值进行了转码
                                canvas.setName(new String(item.getString().getBytes("iso8859-1"), "utf-8"));
                            } else if ("price".equals(fieldName)) {//当fieldName为price
                                //设置Canvas对象中的price值为request中的price
                                canvas.setPrice(Integer.valueOf(item.getString()));
                            } else if ("description".equals(fieldName)) {//当fieldName为description
                                //设置Canvas对象中的description值为request中的description
                                canvas.setDescription(new String(item.getString().getBytes("iso8859-1"), "utf-8"));
                            } else if ("canvasId".equals(fieldName)) {
                                //设置Canvas对象中的categoryId值为request中的categoryId
                                canvas.setId(Long.valueOf(item.getString()));
                            } else {//信息是文件格式
                                //设置Canvas的图片属性为request中的图片数据
                                canvas.setSmallImg(item.get());
                            }
                        }
                    }
                    //更新油画信息
                    canvasService.canvasUpdate(canvas);

                    req.getRequestDispatcher("/canvas/list.do").forward(req, resp);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if ("/canvas/delete.do".equals(req.getServletPath())) {
                //需要删除油画的id
                Long canvasId = Long.valueOf(req.getParameter("canvasId"));

                canvasService.canvasDelete(canvasId);



            req.getRequestDispatcher("/canvas/list.do").forward(req, resp);

        }
    }

    @Override
    public void destroy() {
        super.destroy();
        canvasService = null;
        categoryService = null;
    }
}
