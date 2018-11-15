package com.potato.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.potato.entity.Seckill;

/**
 * 配置junit和spring整合,junit启动时加载spring IOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
@Slf4j
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() {
    }

    @Test
    public void queryById() {
        long seckillId = 1000;
        Seckill seckill = seckillDao.queryById(seckillId);
        assertEquals("1000元秒杀iphone6", seckill.getName());
        log.info("seckill = {}", seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckillList = seckillDao.queryAll(0, 10);
        assertEquals(4, seckillList.size());
    }
}