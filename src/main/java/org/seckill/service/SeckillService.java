package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillExecption;
import org.seckill.exception.SeckillClosedExecption;
import org.seckill.exception.SeckillExecption;

import java.util.List;

/**使用者的角度设计
 * 三个方面：方法定义粒度，参数简洁，返回类型（return/exception）
 * @Author: haojunhao
 * @Date: Create in 2017/9/24
 **/
public interface SeckillService {

    /**
     *获取秒杀商品列表
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 根据id获取秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getSeckillById(long seckillId);

    /**
     * 秒杀开始是=时暴露秒杀地址；
     * 否则显示当前系统时间和秒杀开启时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillExecption,RepeatKillExecption,SeckillClosedExecption;

}
