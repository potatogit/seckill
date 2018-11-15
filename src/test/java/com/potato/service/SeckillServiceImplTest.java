package com.potato.service;

import static org.junit.Assert.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.potato.Exception.SeckillException;
import com.potato.dto.Exposer;
import com.potato.dto.SeckillExecution;
import com.potato.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml" })
@Slf4j
public class SeckillServiceImplTest {
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        log.info("list: {}", seckillList);
    }

    @Test
    public void getSeckillById() {
        Seckill seckill = seckillService.getSeckillById(1000);
        assertEquals(1000, seckill.getSeckillId());

    }

    @Test
    public void exposeSeckillUrl() {
        Exposer exposer = seckillService.exposeSeckillUrl(1004);
        log.info("exposer = {}", exposer);
    }

    @Test
    public void executeSeckill() {
        long secillId = 1004L;
        long userPhone = 19912312311L;
        String md5 = "1a128cfc2a55e7ed7cf3cd3153c9caef";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(secillId, userPhone, md5);
            log.info("seckillExecution = {}", seckillExecution);
        } catch (SeckillException e) {
            log.error("SeckillException {}", e.getMessage());
        }
    }
}