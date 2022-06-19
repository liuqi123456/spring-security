package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class HttpSessionConfig2 {

//    /**
//     * 创建Redis连接,默认是连接本地localhost:6379
//     */
//    @Bean
//    public RedisConnectionFactory connectionFactory(){
//        return new JedisConnectionFactory();
//    }

//    /**
//     * HttpSession的事件监听,改用Session提供的会话注册表.
//     * 把HttpSessionEventPublisher注册到IOC容器中，才能将java事件转化成Spring事件
//     */
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

}
