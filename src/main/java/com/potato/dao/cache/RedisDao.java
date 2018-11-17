package com.potato.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.potato.entity.Seckill;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
public class RedisDao {
	private final JedisPool jedisPool;

	public RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}

	//使用这种序列化方法最节省空间
	private RuntimeSchema<Seckill> runtimeSchema = RuntimeSchema.createFrom(Seckill.class);

	public Seckill getSeckill(long seckillId) {
		Jedis jedis = jedisPool.getResource();
		try {
			String key = "seckill:" + seckillId;
			byte[] bytes = jedis.get(key.getBytes());

			if(bytes != null) {
				Seckill seckill = runtimeSchema.newMessage();
				ProtostuffIOUtil.mergeFrom(bytes, seckill, runtimeSchema);
				return seckill;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("setSeckill error: ", e);
			return null;
		} finally {
			jedis.close();
		}
	}

	public String putSeckill(Seckill seckill) {
		Jedis jedis = jedisPool.getResource();
		try {
			String key = "seckill:" + seckill.getSeckillId();
			byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, runtimeSchema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			String setex = jedis.setex(key.getBytes(), 3600, bytes);// 1 hour cache, if succeed, return ok ,else return error message
			return setex;
		} catch (Exception e) {
			log.error("putSeckill error: ", e);
			return null;
		}
		finally {
			jedis.close();
		}
	}
}
