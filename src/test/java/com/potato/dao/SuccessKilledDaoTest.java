package com.potato.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.potato.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
@Slf4j
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        long seckillId = 1000L;
        long phone = 18812341234L;
        int successKilled = successKilledDao.insertSuccessKilled(seckillId, phone);
        log.info("{} row changed", successKilled);
    }

    @Test
    public void queryByIdWithSeckill() {
        long seckillId = 1000L;
        long phone = 18812341234L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, phone);
        assertEquals(1000, successKilled.getSeckillId());
        log.info("successkilled = {}", successKilled);
    }
}