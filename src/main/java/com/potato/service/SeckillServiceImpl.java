package com.potato.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.potato.dao.cache.RedisDao;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.potato.Exception.RepeatSeckillException;
import com.potato.Exception.SeckillCloseException;
import com.potato.Exception.SeckillException;
import com.potato.dao.SeckillDao;
import com.potato.dao.SuccessKilledDao;
import com.potato.dto.Exposer;
import com.potato.dto.SeckillExecution;
import com.potato.entity.Seckill;
import com.potato.entity.SuccessKilled;

import enums.SeckillStatEnum;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    private SeckillDao seckillDao;

    private SuccessKilledDao successKilledDao;

    private RedisDao redisDao;

    private static final String salt = "fj;adkjf098uf -98-(&*Y*p9y8fiausyf9*";

    @Autowired
    public SeckillServiceImpl(SeckillDao seckillDao, SuccessKilledDao successKilledDao, RedisDao redisDao) {
        this.seckillDao = seckillDao;
        this.successKilledDao = successKilledDao;
        this.redisDao = redisDao;
    }

    @Override public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override public Exposer exposeSeckillUrl(long seckillId) {
	    Seckill seckill = redisDao.getSeckill(seckillId);
	    if(seckill == null) {
		    seckill = seckillDao.queryById(seckillId);
		    if(Objects.isNull(seckill)) {
			    return new Exposer(false, seckillId);
		    }
	        redisDao.putSeckill(seckill);
	    }
        long curTime = System.currentTimeMillis();
        long startTime = seckill.getStartTime().getTime();
        long endTime = seckill.getEndTime().getTime();
        if(curTime > startTime && curTime < endTime) {
            String md5 = getMd5(seckillId);
            return new Exposer(true, seckillId, md5);
        } else {
            return new Exposer(false, seckillId, curTime, startTime, endTime);
        }
    }

    private String getMd5(long seckillId) {
        String base = seckillId + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    /**
     * 1.约定一致风格
     * 2.保证事务方法执行时间尽可能短,不要穿插RPC/HTTP请求
     *
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException {
        try{
            if (md5 == null || md5.isEmpty() || !md5.equals(getMd5(seckillId))) {
                throw new SeckillException("verification fail");
            } else {
                Date curTime = new Date();
                int updateRowNum = seckillDao.reduceNumber(seckillId, curTime);
                if (updateRowNum <= 0) {
                    throw new SeckillCloseException("Seckill is over!");
                } else {
                    int insertedNum = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                    if (insertedNum <= 0) {
                        throw new RepeatSeckillException("Seckilll repeated!");
                    } else {
                        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                        return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                    }
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatSeckillException e2) {
            throw e2;
        } catch (Exception e) {
            log.error("seckill error: {}", e.getMessage());
            throw new SeckillException("Seckill inner error!");
        }
    }
}
