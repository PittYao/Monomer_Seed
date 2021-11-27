package com.bugprovider.seed.config;

import com.bugprovider.seed.common.service.RedisService;
import com.bugprovider.seed.common.service.RedisServiceImpl;
import org.springframework.context.annotation.Bean;

/**
 * Redis基础配置
 */
public class BaseRedisConfig {

    @Bean
    public RedisService redisService(){
        return new RedisServiceImpl();
    }

}
