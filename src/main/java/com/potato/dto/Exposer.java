package com.potato.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 暴露秒杀地址DTO
 */
@Setter
@Getter
public class Exposer {

    private boolean exposed;

    private long seckillId;

    private long now;

    // 秒杀开始时间
    private long start;

    private long end;

    private String md5;

    public Exposer(boolean exposed, long now, long start, long end) {
        this.exposed = exposed;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckillId, String md5) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.md5 = md5;
    }

    public Exposer(boolean exposed, long seckillId) {

        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
