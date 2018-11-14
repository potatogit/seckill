package com.potato.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SuccessKilled {
    private long seckillId;
    private long userPhone;
    private short state;
    private Date createTime;

    private Seckill seckill;

    @Override public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", seckill=" + seckill +
                '}';
    }
}
