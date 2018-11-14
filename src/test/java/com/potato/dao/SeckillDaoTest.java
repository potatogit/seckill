package com.potato.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.potato.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
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
        assertEquals("", seckill.getName());
    }

    @Test
    public void queryAll() {
    }
}