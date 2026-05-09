package com.zcg.test;

import com.util.JedisConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;

//import static com.util.Constants.JEDIS_PORT;
//import static com.util.Constants.VIRTUAL_MACHINE_IP;

public class JedisTest {
    private Jedis jedis;

    // 快捷键Alt+Insert 选SetMethod
    @BeforeEach
    void setUp() {
        // 建立连接
        // jedis = new Jedis(VIRTUAL_MACHINE_IP, JEDIS_PORT);
        jedis = JedisConnectionFactory.getJedis();
        // 设置密码
        jedis.auth("root");
        // 选择库
        jedis.select(0);
    }

    // 测试
    @Test
    void testHash() {
        // 插入Jedis数据
        jedis.hset("user:1", "name", "Jack");
        jedis.hset("user:1", "age", "21");
        // 获取
        Map<String, String> userMap = jedis.hgetAll("user:1");
        System.out.println(userMap);
    }

    // 释放资源
    @AfterEach
    void tearDown() {
        if (jedis != null){
            jedis.close();
        }
    }
}
