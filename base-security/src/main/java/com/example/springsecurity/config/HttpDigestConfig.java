package com.example.springsecurity.config;//package com.example.springsecurity.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
///**
// * http摘要认证的时候密码不能加密，所以不能配置PasswordConfig这样的配置
// */
//@Component
//public class HttpDigestConfig {
//
//    /**
//     * 摘要身份验证入口点
//     *
//     * 自定义 DigestAuthenticationEntryPoint
//     * DigestAuthenticationEntryPoint 用于配置http摘要认证部分允许自定义的数据
//     * @return
//     */
//    @Bean
//    public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint(){
//        DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
//        digestAuthenticationEntryPoint.setRealmName("realName");
//        digestAuthenticationEntryPoint.setKey("tony");
//        return digestAuthenticationEntryPoint;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//}
