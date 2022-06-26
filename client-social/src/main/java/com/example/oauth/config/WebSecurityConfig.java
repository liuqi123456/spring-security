package com.example.oauth.config;

import com.example.oauth.registrations.CompositeOAuth2AccessTokenResponseClient;
import com.example.oauth.registrations.CompositeOAuth2UserService;
import com.example.oauth.registrations.qq.QQOAuth2AccessTokenResponseClient;
import com.example.oauth.registrations.qq.QQOAuth2UserService;
import com.example.oauth.registrations.qq.QQUserInfo;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 401 代表用户认证失败
 * 403 代表用户授权失败
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String QQRegistrationId = "qq";
    public static final String WeChatRegistrationId = "wechat";

    public static final String LoginPagePath = "/login/oauth2";


    /**
     * HttpSecurity 设计为了链式调用，使用 and方法结束当前标签，上下文才会回到HttpSecurity
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(LoginPagePath).permitAll()
                .anyRequest()
                .authenticated();
        http.oauth2Login()
                // 使用CompositeOAuth2AccessTokenResponseClient
                .tokenEndpoint().accessTokenResponseClient(this.accessTokenResponseClient())
                .and()
                .userInfoEndpoint()
                .customUserType(QQUserInfo.class, QQRegistrationId)
                // 使用CompositeOAuth2UserService
                .userService(oauth2UserService())
                // 可选，要保证与redirect-uri-template匹配
                .and()
                .redirectionEndpoint().baseUri("/register/social/*");

        //自定义登录页
        http.oauth2Login().loginPage(LoginPagePath);
    }

    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        CompositeOAuth2AccessTokenResponseClient client = new CompositeOAuth2AccessTokenResponseClient();
        // 加入QQ自定义QQOAuth2AccessTokenResponseClient
        client.getOAuth2AccessTokenResponseClients().put(QQRegistrationId, new QQOAuth2AccessTokenResponseClient());
        return client;
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        CompositeOAuth2UserService service = new CompositeOAuth2UserService();
        // 加入QQ自定义QQOAuth2UserService
        service.getUserServices().put(QQRegistrationId, new QQOAuth2UserService());
        return service;
    }

}
