package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * @Author: haojunhao
 * @Date: Create in 2017/9/23
 **/
public interface SuccesskilledDao {

    /**
     * 插入秒杀记录，可过滤重复，联合主键
     * @param seckilledId
     * @param userPhone 返回1 成功；返回0 失败
     */
    int insertSuccessSeckilled(@Param("seckillId") long seckilledId, @Param("userPhone") long userPhone);

    /**
     * 根据id,userPhone查询秒杀记录Successkilled，并携带秒杀对象
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
