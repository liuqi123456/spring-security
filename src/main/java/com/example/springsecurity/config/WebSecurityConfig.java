package com.example.springsecurity.config;

import com.example.springsecurity.filter.MyAuthenticationFailureHandler;
import com.example.springsecurity.filter.VerificationCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 401 代表用户认证失败
 * 403 代表用户授权失败
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myWebAuthenticationDetailsSource;

    @Autowired
    private AuthenticationProvider myAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 使用自定义的方式验证 验证码
         */
        auth.authenticationProvider(myAuthenticationProvider);
    }

    /**
     * HttpSecurity 设计为了链式调用，使用 and方法结束当前标签，上下文才会回到HttpSecurity
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/api/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**", "/captcha.jpg").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .formLogin()
            /**
             * 使用自定义的方式验证 验证码
             */
            .authenticationDetailsSource(myWebAuthenticationDetailsSource)
                /** 自定义登录页配置 */
            .loginPage("/myLogin.html")
                .loginProcessingUrl("/auth/form").permitAll()
                .failureHandler(new MyAuthenticationFailureHandler())
//            .loginProcessingUrl("/login")
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        response.setContentType("application/json;charset=UTF-8");
//                        PrintWriter printWriter = response.getWriter();
//                        printWriter.write("{\"error_code\":\"1\",\"message\":\"欢迎登录系统\"}");
//                    }
//                })
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                        response.setContentType("application/json;charset=UTF-8");
//                        response.setStatus(401);
//                        PrintWriter printWriter = response.getWriter();
//                        printWriter.write("{\"error_code\":\"401\",\"name\":\""+ exception.getClass() +"\",\"message\":\""+ exception.getMessage() +"\"}");
//                    }
//                })
//            .permitAll()
            .and()
            .sessionManagement().maximumSessions(1)
//
        ;
        /**
         * 使用过滤器验证 验证码
         */
//        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
