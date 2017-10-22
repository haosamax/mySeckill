package org.seckill.exception;

/**
 * 重复秒杀异常(运行期异常，spring接收到会回滚)
 * @Author: haojunhao
 * @Date: Create in 2017/9/24
 **/
public class RepeatKillExecption extends SeckillExecption{
    public RepeatKillExecption(String message) {
        super(message);
    }

    public RepeatKillExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
