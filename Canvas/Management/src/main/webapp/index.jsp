<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <title>CakeManagement</title>
    <!-- 当Management模块启动时，会直接访问欢迎页面index.jsp，
    并通过该页面访问CakeServlet获得全部蛋糕数据并转发到cake_list.jsp-->
    <meta http-equiv="refresh" content="0;url=<%=request.getContextPath()%>/canvas/list.do">
  </head>
</html>
