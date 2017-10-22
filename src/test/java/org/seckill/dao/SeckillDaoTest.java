package org.seckill.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: haojunhao
 * @Date: Create in 2017/9/23
 **/
/*
配置spring和junit整合,junit启动时加载springIOC容器
spring-test、junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    private final Log log = LogFactory.getLog(SeckillDaoTest.class);

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //注入Dao依赖
    @Resource
    private SeckillDao seckillDao;


    /*
    * org.apache.ibatis.binding.BindingException: Parameter 'seckillId' not found. Available parameters are [0, 1, param1, param2]
    * 参数绑定，用注解制定名字
    */
    @Test
    public void reduceNumber() throws Exception {
        long id = 1000;
        seckillDao.reduceNumber(id, new Date());
    }

    //jdbc.properties中的username，password应变成jdbc.username、jdbc.password
    //<= 在sql中应转义  <!CDATA[[ <= ]]>
    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        log.info(seckill);
    }

    @Test
    public void queryForList() throws Exception {
        List<Seckill> seckills = seckillDao.queryForList(0,3);
        logger.info("seckills={}",seckills);
    }

}