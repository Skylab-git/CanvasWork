package com.imooc.canvas.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 登录
 *
 * @version 1.0
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/login.do".equals(req.getServletPath())) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            //如果用户名和密码相同且不为null，则登录成功
            //这里做了简单处理，设定为用户名和密码相同就允许用户登录，不同则跳转到登录页面
            if (null != username && !"".equals(username.trim()) && username.equals(password)) {
                req.getSession().setAttribute("username", username);
                req.getRequestDispatcher("/canvas/list.do").forward(req, resp);
            } else {
                req.getRequestDispatcher("/loginPrompt.do").forward(req, resp);
            }
        } else if ("/loginPrompt.do".equals(req.getServletPath())) {
            req.getRequestDispatcher("/WEB-INF/views/biz/login.jsp").forward(req, resp);
        }
    }
}
