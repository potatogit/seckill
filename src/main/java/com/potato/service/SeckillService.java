package com.potato.service;

import java.util.List;

import com.potato.Exception.SeckillException;
import com.potato.dto.Exposer;
import com.potato.dto.SeckillExecution;
import com.potato.entity.Seckill;

public interface SeckillService {
    /**
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     *
     * @param seckillId
     * @return
     */
    Seckill getSeckillById(long seckillId);

    /**
     * 秒杀开启时,暴露秒杀地址,否则显示系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exposeSeckillUrl(long seckillId);

    /**
     *
     * @param seckill
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckill, long userPhone, String md5) throws SeckillException;


}
