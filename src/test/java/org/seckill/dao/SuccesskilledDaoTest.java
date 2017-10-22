package org.seckill.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;


/**
 * @Author: haojunhao
 * @Date: Create in 2017/9/24
 **/
/*
配置spring和junit整合,junit启动时加载springIOC容器
spring-test、junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})

public class SuccesskilledDaoTest {

    private final Log log = LogFactory.getLog(SeckillDaoTest.class);

    @Resource
    private SuccesskilledDao successkilledDao;

    //第一次：insertCount:1
    //第二次：insertCount:0
    @Test
    public void insertSuccessSeckilled() throws Exception {
        long id = 1000;
        long userPhone = 15698560187l;
        int insertCount = successkilledDao.insertSuccessSeckilled(id,userPhone);
        log.info(insertCount);
    }

    //sql中点写成了逗号
    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1000;
        long userPhone = 15698560187l;
        SuccessKilled killed = successkilledDao.queryByIdWithSeckill(id,userPhone);
        log.info(killed);
    }

}