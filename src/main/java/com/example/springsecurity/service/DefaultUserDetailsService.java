//package com.example.springsecurity.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//
//@Service
//public class DefaultUserDetailsService {
//
//    @Autowired
//    private DataSource dataSource;
//
//    /**
//     * JdbcUserDetailsManager 默认的数据库模型
//     * "D:/devs/maven_local_repository/org/springframework/security/spring-security-core/5.7.1/spring-security-core-5.7.1.jar! /org/springframework/security/core/userdetails/jdbc/users.ddl"
//     * @return
//     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
//        manager.setDataSource(dataSource);
//        if (!manager.userExists("user")) {
//            manager.createUser(User.withUsername("user").password("123456").roles("USER").build());
//        }
//        if (!manager.userExists("admin")) {
//            manager.createUser(User.withUsername("admin").password("123456").roles("USER", "ADMIN").build());
//        }
//        return manager;
//    }
//
//}
