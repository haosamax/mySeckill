package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillExecption;
import org.seckill.exception.SeckillClosedExecption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: haojunhao
 * @Date: Create in 2017/10/7
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckills = seckillService.getSeckillList();

        logger.info("list={}",seckills);

    }

    @Test
    public void getSeckillById() throws Exception {
        Seckill seckill = seckillService.getSeckillById(1001);
        logger.info("seckill={}",seckill);
    }

    //集成测试代码完整逻辑
    @Test
    public void seckillLogic() throws Exception {
        long id = 1001L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            logger.info("exposer={}",exposer);
            //执行秒杀
            long phone = 15698560181L;
            SeckillExecution execution = null;
            try {
                //org.seckill.exception.RepeatKillExecption: seckill repeate；
                //org.seckill.exception.SeckillExecption: seckill data rewrite
                execution = seckillService.executeSeckill(id,phone,exposer.getMd5());
                logger.info("execution={}",execution);
            }catch (RepeatKillExecption e){
                logger.error(e.getMessage());
            }catch (SeckillClosedExecption e){
                logger.error(e.getMessage());
            }
        }else {
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }

        //exposer=Exposer{exposed=true, md5='763964efe3ada7bb228f67d10db26126', seckillId=1000, now=0, start=0, end=0}
    }


}