<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入jstl--%>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表页</title>

    <%--静态包含：回合并过来一起编译成servlet；（动态包含，作为独立servlet，变异之后合并生成html）。一个servlet，多个servlet--%>
    <%@include file="common/head.jsp"%>
</head>
<body>

    <%--页面展示部分--%>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表页</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <td>名称</td>
                            <td>库存</td>
                            <td>开始时间</td>
                            <td>结束时间</td>
                            <td>创建时间</td>
                            <td>详情页</td>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach items="${list}" var="row">
                            <tr>
                                <td>${row.name}</td>
                                <td>${row.number}</td>
                                <td>
                                    <fmt:formatDate value="${row.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${row.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${row.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td><a class="btn btn-info" href="/seckill/${row.seckillId}/detail" target="_blank">link</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </div>





</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
