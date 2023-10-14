package com.icyrain11.redis.springbootladderredis6x.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson config
 * @author icyrain11
 * @version 17
 */
@Configuration
public class RedissonConfig {


    @Bean
    public RedissonClient redisClient() {
        // 配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        // 创建RedissonClient对象
        return Redisson.create(config);
    }


}
