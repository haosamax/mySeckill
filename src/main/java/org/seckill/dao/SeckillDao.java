package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;
import java.util.Date;
import java.util.List;

/**
 * @Author: haojunhao
 * @Date: Create in 2017/9/23
 **/
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param currentTime
     * @return 返回 >1 成功；反之 失败
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 查询
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryForList(@Param("offset") int offset, @Param("limit") int limit);
}
