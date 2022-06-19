package com.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * 启动基于Redis的httpSession实现
 */
@EnableRedisHttpSession
public class HttpSessionConfig {

    @Autowired
    private FindByIndexNameSessionRepository<? extends Session> mySessionRepository;

    /**
     * SpringSessionBackedSessionRegistry 是session为Spring security提供的用于在集群环境下控制会话并发的会话注册表实现类
     * @return
     */
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry<>(mySessionRepository);
    }

}
