//存放交互逻辑
//模块化

var seckill = {
    //封装秒杀相关ajax的url
    URL : {

    },
    //验证手机号
    validatePhone : function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }
        return false;
    },
    
    
    //详情页秒杀逻辑
    detail : {
        init : function (param) {
            //用户手机验证和登陆，计时交互
            //规划我们的交互流程
            //获取cookie中手机号
            var killPhone = $.cookie('killPhone');
            var startTime = param['startTime'];
            var endTime = param['endTime'];
            var seckillId = param['seckillId'];

            //验证手机号

            if (!seckill.validatePhone(killPhone)) {
                //绑定手机号
                //控制输出
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false//关闭键盘事件
                });

                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        //写电话到cookie
                        $.cookie('killPhone', inputPhone,
                            {expires: 7, path: '/'});//有效期7天，路径seckill下有效

                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage')
                            .hide()
                            .html('<lable class="label label-danger">手机号码错误</lable>').show(300);

                    }

                });
            }

        }
    }
}
