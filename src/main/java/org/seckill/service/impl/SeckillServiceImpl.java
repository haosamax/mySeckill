package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccesskilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillSateEnum;
import org.seckill.exception.RepeatKillExecption;
import org.seckill.exception.SeckillClosedExecption;
import org.seckill.exception.SeckillExecption;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: haojunhao
 * @Date: Create in 2017/9/24
 **/
/*
不知道类型用@componet dao用@Controller  service用@Service
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String salt = "@#$@DESR#E%$W@#$@~0-123><<@!%^&";

    @Autowired//@Resource，@Inject(J2EE的规范注解)
    private SeckillDao seckillDao;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SuccesskilledDao successkilledDao;


    public List<Seckill> getSeckillList() {
        return seckillDao.queryForList(0,4);
    }

    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {

        //优化点：缓存优化  超时优化
        //1.从redis中访问
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if (seckill==null){
                return new Exposer(false,seckillId);
            }else {
                redisDao.putSeckill(seckill);
            }
        }


        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        Date now = new Date();
        if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()){
            return new Exposer(false,now.getTime(),startTime.getTime(),endTime.getTime(),seckillId);
        }

        //转化特定字符串的过程，不可逆
        String md5 = getMd5(seckillId);

        return new Exposer(true,md5,seckillId);
    }

    @Transactional
    /**
     * 1.开发团队达成一致，明确标注事物方法的编程风格
     * 2.保证事物方法执行时间尽可能短，不要穿插其他网络操作（RPC/HTTP）或者剥离出去
     * 3.不是所有的方法都需要事物，比如只有一条修改操作或只读操作不需要控制
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillExecption, RepeatKillExecption, SeckillClosedExecption {
        //是否匹配seckillid与md5
        if (md5 == null || !md5.equals(getMd5(seckillId))){
            throw new SeckillExecption("seckill data rewrite");
        }



        //执行秒杀逻辑：记录购买明细，减库存
        Date now = new Date();


        try {
            //记录购买行为
            int insertCount = successkilledDao.insertSuccessSeckilled(seckillId,userPhone);

            if (insertCount<=0){
                //重复秒杀
                throw new RepeatKillExecption("seckill repeate");
            }else {
                //减库存，热点商品竞争
                int updateCount = seckillDao.reduceNumber(seckillId, now);

                if (updateCount<=0){
                    //没有更新到记录
                    throw new SeckillClosedExecption("seckill is closed");
                }else {
                    //秒杀成功
                    SuccessKilled successKilled = successkilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillSateEnum.SUCCESS, successKilled);
                }
            }
        //抛出秒杀关闭异常
        }catch (SeckillClosedExecption c){
            throw c;
         //抛跑出秒杀重复异常
        }catch (RepeatKillExecption r){
            throw r;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有检查型异常，转化为运行时异常（这样spring的声明式事物才会回滚）
            throw new SeckillExecption("seckill inner error "+e.getMessage());
        }
    }

    private String getMd5(long seckillId){
        String base = seckillId+"/"+salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
