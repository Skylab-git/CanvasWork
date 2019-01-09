<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link rel="stylesheet" type="text/css" href="/style/common.css" />
    <link rel="stylesheet" type="text/css" href="/style/index.css" />
</head>
<body>
    <div class="header">
        <div class="logo f1">
            <img src="/image/logo.png">
        </div>
        <div class="auth fr">
            <ul>
                <li><a href="#">登录</a></li>
                <li><a href="#">注册</a></li>
            </ul>
        </div>
    </div>
    <div class="content">
        <div class="banner">
            <img class="banner-img" src="/image/welcome.png" width="732px" height="372" alt="图片描述">
        </div>

        <div class="img-content">
            <ul>
                <c:forEach items="${canvasList}" var="canvas">
                <li>
                    <img class="img-li-fix" src="/canvas/getImg.do?id=${canvas.id}" class="img-responsive" alt=""/>
                    <div class="info">
                        <h3 class="imgTitle">${canvas.name}</h3>
                        <p>
                            ${canvas.description}
                        </p>
                        <div class="btn">
                            <a href="#" class="edit">详情</a> 
                        </div>
                    </div>
                </li>
                </c:forEach>


            </ul>
        </div>

        <script type="text/javascript">
            function submitMessageForm(flag) {
                if ('first' === flag) {
                    document.getElementById('page').value = 1;
                } else if ('pre' === flag) {
                    var current = Number(document.getElementById('page').value);
                    if (current > 1) {
                        document.getElementById('page').value = current - 1;
                    }
                } else if ('next' === flag) {
                    var current = Number(document.getElementById('page').value);
                    var last = Number(document.getElementById('last').value);
                    // if (current < last) {
                        document.getElementById('page').value = current + 1;
                    // }
                } else if ('last' === flag) {
                    var last = Number(document.getElementById('last').value);
                    document.getElementById('page').value = last < 1 ? 1 : last;
                }
                document.getElementById('canvasForm').submit();
            }
        </script>
        <div class="page-nav">
            <form id="canvasForm" action="/canvas/list.do" method="post">
                <input type="hidden" id="page" name="page" value="${page}">
                <input type="hidden" id="last" name="last" value="${last}">
                <input type="hidden" id="categoryId" name="categoryId" value="${categoryId}">
                <ul class="pagination">
                <li><a href="javascript:void(0)" onclick="submitMessageForm('first')">首页</a></li>
                <li><a href="javascript:void(0)" onclick="submitMessageForm('pre')">上一页</a></li>
                <li><a href="javascript:void(0)">当前第${page}页</a></li>
                <li><a href="javascript:void(0)" onclick="submitMessageForm('next')">下一页</a></li>
                <li><a href="javascript:void(0)" onclick="submitMessageForm('last')">尾页</a></li>
            </ul>
            </form>
        </div>
    </div>

    <div class="footer">
        <p><span>M-GALLARY</span>©2017 POWERED BY IMOOC.INC</p>
    </div>
</body>
</html>