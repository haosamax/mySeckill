package org.seckill.exception;

/**
 * 秒杀关闭异常
 * @Author: haojunhao
 * @Date: Create in 2017/9/24
 **/
public class SeckillClosedExecption extends SeckillExecption{
    public SeckillClosedExecption(String message) {
        super(message);
    }

    public SeckillClosedExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
