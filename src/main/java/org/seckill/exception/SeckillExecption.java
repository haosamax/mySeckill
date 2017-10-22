package org.seckill.exception;

/**
 * 秒杀异常
 * @Author: haojunhao
 * @Date: Create in 2017/9/24
 **/
public class SeckillExecption extends RuntimeException {
    public SeckillExecption(String message) {
        super(message);
    }

    public SeckillExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
