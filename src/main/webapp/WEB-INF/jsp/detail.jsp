<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入jstl--%>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%--静态包含：回合并过来一起编译成servlet；（动态包含，作为独立servlet，变异之后合并生成html）。一个servlet，多个servlet--%>
    <%@include file="common/head.jsp"%>
</head>
<body>

    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">
                <h1>${seckill.name}</h1>
            </div>

            <div class="panel-body">
                <h2 class="text-danger">
                    <%--显示time图标--%>
                    <span class="glyphicon glyphicon-time"></span>
                    <%--展示倒计时--%>
                    <span class="glyphicon" id="seckill-box"></span>
                </h2>
            </div>
        </div>
    </div>

    <%--弹出登陆层，输入电话号码--%>
    <div id="killPhoneModal" class="modal fade">
        <div class="modal-dailog" style="width: 60%;text-align: center;margin: 0 auto;">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                    </h3>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8" col-xs-offset-2>
                            <input name="killPhone" type="text" id="killPhoneKey"
                                   placeholder="请填写手机号^o^" class="form-control">
                        </div>
                    </div>

                    <div class="modal-footer">
                        <%--验证信息--%>
                        <span id="killPhoneMessage" class="glyphicon"></span>
                        <button type="button" id="killPhoneBtn" class="btn btn-success">
                            <span class="glyphicon glyphicon-phone"></span>
                            Submit
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>


</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<%--使用CDN 获取公共js http://www.bootcdn.cn--%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--countDown--%>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>


<%--开始编写交互逻辑--%>
<script src="/resources/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">

    //使用EL表达式传入参数
    $(function () { //jquery 页面初始化直接调用
        seckill.detail.init({
            seckillId : ${seckill.seckillId},
            startTime : ${seckill.startTime.time},//毫秒
            endTime : ${seckill.endTime.time}
        });
    })



</script>

</html>
