package com.imooc.canvas.filter;


import javax.servlet.*;
import java.io.IOException;

public class CharsetEncodingFilter implements Filter {

    private String encoding = "utf-8";

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
