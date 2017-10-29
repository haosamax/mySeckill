//存放交互逻辑
//模块化

var seckill = {
    //封装秒杀相关ajax的url
    URL : {
        now : function () {
            return '/seckill/time/now';
        },
        exposer : function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution : function (seckillId, md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution'
        }
    },
    //验证手机号
    validatePhone : function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }
        return false;
    },

    handleSeckill:function (seckillId, node) {
        //获取秒杀地址，显示秒杀按钮，执行秒杀

        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>')//按钮

        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            if(result && result['success']){
                var exposer = result['data'];
                if (exposer['exposed']){
                    //开启秒杀，获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);

                    console.log('killUrl: '+killUrl)

                    //绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        //执行秒杀请求
                        //1.先禁用按钮
                        $(this).addClass('disable');
                        //2.发送请求
                        $.post(killUrl, function (result) {
                            if (result && result['success']){
                                var killResult = result['data'];

                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo']
                                //3.显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>')
                            }
                        })

                    });

                    node.show();


                }else {
                    //未开启秒杀(用户机子时间太快了)，重新计时
                    var start = exposer['start'];
                    var now = exposer['now'];
                    var end = exposer['end'];

                    seckill.countdown(seckillId,now,start,end);
                }
            }else {
                console.log(result)
            }
        })


    },

    countdown : function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');

        nowTime = new Date(nowTime).getTime();
        //判断时间
        if(nowTime > endTime){
            alert(1)
            //秒杀结束
            seckillBox.html('秒杀结束')
        }else if(nowTime.ge < startTime){
            alert(nowTime+ ' '+startTime)
            //秒杀未开始，计时事件
            var killTime = new Date(Number(startTime) + 1000);
            seckillBox.countdown(killTime, function(event){
                //日期变换的回调函数
                //转换时间格式输出
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                //时间完成后回调事件
            }).on('finish.countdown',function () {


                seckill.handleSeckill(seckillId, seckillBox);
            });
        }else {
            //秒杀开始
            alert(3)
            seckill.handleSeckill(seckillId, seckillBox)


        }

    },


    
    //详情页秒杀逻辑
    detail : {
        init : function (param) {
            //用户手机验证和登陆，计时交互
            //规划我们的交互流程
            //获取cookie中手机号
            var killPhone = $.cookie('killPhone');

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


            //已经登陆获取系统时间

            var startTime = param['startTime'];
            var endTime = param['endTime'];
            var seckillId = param['seckillId'];

            $.get(seckill.URL.now(),function (result) {

                if(result && result['success']){
                    var nowTime = result['error'];

                    //时间控制
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                }else {
                    console.log(result);
                }
            });




        }
    }
}
