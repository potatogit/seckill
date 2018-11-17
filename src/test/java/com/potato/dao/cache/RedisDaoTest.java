package com.potato.dao.cache;

import com.potato.dao.SeckillDao;
import com.potato.entity.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
@Slf4j
public class RedisDaoTest {
	private static final long seckillId = 1000L;
	@Autowired
	private RedisDao redisDao;

	@Autowired
	private SeckillDao seckillDao;

	@Test
	public void testSeckill() {
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			String result = redisDao.putSeckill(seckill);
			log.info("put redis result: {}", result);
			seckill = redisDao.getSeckill(seckillId);
			log.info("get redis result: {}", seckill);
		} else {
			log.info("get redis result: {}", seckill);
		}
	}

}