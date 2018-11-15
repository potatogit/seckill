package com.potato.dao;

import static org.testng.Assert.*;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.potato.entity.Seckill;

@Test
@Slf4j
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class SeckillDaoTestNG extends AbstractTestNGSpringContextTests {
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testReduceNumber() {
    }

    @Test
    public void testQueryById() {
        long seckillId = 1000;
        Seckill seckill = seckillDao.queryById(seckillId);
        assertEquals("1000元秒杀iphone6", seckill.getName());
        log.info("seckill = {}", seckill);
    }

    @Test
    public void testQueryAll() {
    }
}