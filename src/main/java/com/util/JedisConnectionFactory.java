package com.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static com.util.Constants.JEDIS_PORT;
import static com.util.Constants.VIRTUAL_MACHINE_IP;

// 创建Jedis的线程池
public class JedisConnectionFactory {
    private static final JedisPool jedisPool;

    static{
        // 配置连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接
        poolConfig.setMaxTotal(8);
        // 最大空闲连接
        poolConfig.setMaxIdle(8);
        // 最小空闲连接
        poolConfig.setMinIdle(0);
        // 设置最长等待时间（单位:ms）
        poolConfig.setMaxWaitMillis(1000);
        // 创建连接池对象
        jedisPool = new JedisPool(poolConfig, VIRTUAL_MACHINE_IP, JEDIS_PORT, 1000, "root");
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
