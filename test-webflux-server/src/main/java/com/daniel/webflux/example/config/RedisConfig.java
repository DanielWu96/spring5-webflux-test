package com.daniel.webflux.example.config;


import com.daniel.webflux.example.helper.RedisHelper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: daniel
 * @Description: 客户端使用Lettuce,多数据源配置
 * @Date: 2020/4/1 17:59
 */
@Configuration
public class RedisConfig {

    @Value("${redis.idfa2uid.address}")
    private String idfaUidRedisAddress;
    @Value("${redis.mac2imei.address}")
    private String macImeiRedisAddress;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.cluster.lettuce.pool")
    public GenericObjectPoolConfig redisPool() {
        return new GenericObjectPoolConfig();
    }

    /**
     * 配置第一个数据源的RedisTemplate
     *
     * @return
     */
    @Bean("idfa2uidRedisTemplate")
    public ReactiveRedisTemplate lettuceConnectionFactory(@Autowired GenericObjectPoolConfig poolConfig) {
        LettuceConnectionFactory factory = getConnectionFactory(poolConfig, idfaUidRedisAddress);
        //初始化实例
        factory.afterPropertiesSet();
        return new ReactiveStringRedisTemplate(factory);
    }

    /**
     * 配置mac2imei数据源的RedisTemplate
     *
     * @return
     */
    @Bean("mac2imeiRedisTemplate")
    public ReactiveRedisTemplate secondaryRedisTemplate(@Autowired GenericObjectPoolConfig poolConfig) {

        LettuceConnectionFactory factory = getConnectionFactory(poolConfig,macImeiRedisAddress);
        //初始化实例
        factory.afterPropertiesSet();
        return new ReactiveStringRedisTemplate(factory);
    }

    private LettuceConnectionFactory getConnectionFactory(GenericObjectPoolConfig poolConfig, String address) {
        Map<String, Object> source = new HashMap<>(8);
        source.put("spring.redis.cluster.nodes", address);
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
        return factory;
    }

    @Bean
    public RedisHelper redisHelper() {
        return new RedisHelper();
    }

}
