package com.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

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

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SpringSessionBackedSessionRegistry redisSessionRegistry;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        /**
//         * 使用自定义的方式验证 验证码
//         */
//        auth.authenticationProvider(myAuthenticationProvider);
//    }

//    /**
//     * 摘要身份验证入口点
//     */
//    @Autowired
//    private DigestAuthenticationEntryPoint digestAuthenticationEntryPoint;
//
//    /**
//     * 摘要认证过滤器
//     * 过滤器指定了 DigestAuthenticationEntryPoint 和 userDetailsService
//     * @return
//     */
//    public DigestAuthenticationFilter digestAuthenticationFilter(){
//        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
//        digestAuthenticationFilter.setUserDetailsService(userDetailsService);
//        digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint);
//        return digestAuthenticationFilter;
//    }

    /**
     * HttpSecurity 设计为了链式调用，使用 and方法结束当前标签，上下文才会回到HttpSecurity
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 持久化方式
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
        http
            .authorizeRequests()
                .antMatchers("/admin/api/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**", "/captcha.jpg").permitAll()
            .anyRequest().authenticated()
            .and()
                .csrf()
                /**
                 * 直接禁用csrf，避免被攻击
                 */
                .disable()
                /**
                 * 它将csrfToken值存储再用户的cookie内
                 */
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                /**
                 * http基本认证
                 */
//                .httpBasic().and()
                /**
                 * http摘要认证
                 */
//                .exceptionHandling().authenticationEntryPoint(digestAuthenticationEntryPoint)
//                .and()
//                .addFilter(digestAuthenticationFilter())
                /**
                 * security 提供的登录页面
                 */
            .formLogin()
//            /**
//             * 使用自定义的方式验证 验证码
//             */
//            .authenticationDetailsSource(myWebAuthenticationDetailsSource)
//            .and()
            /**
             * 增加自动登录功能
             */
            // 散列方式
//            .rememberMe().userDetailsService(userDetailsService).key("kevin_springsecurity")
                /**
                 * 持久化方式 可以推断该令牌是否被盗用
                 */
//            .rememberMe().userDetailsService(userDetailsService).tokenRepository(jdbcTokenRepository)
                // 设置超时时间
//                .tokenValiditySeconds(60)
            /**
             * 默认提供了一个 /logout路由，
             *  安全的注销登录状态，使httpSession失效，清空已配置的Remember-me，情况SecurityContextHolder,并注销成功后重定向到/login?logout
             * 自定义注销登录策略
             */
//            .and()
//            .logout()
//                .logoutUrl("/myLogout")
//                .logoutSuccessUrl("/")
//                // logoutSuccessHandler 比 logoutSuccessUrl 更灵活
//                .logoutSuccessHandler(new LogoutSuccessHandler() {
//                    @Override
//                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//                    }
//                })
//                // 使该用户的HttpSession失效
//                .invalidateHttpSession(true)
//                // 注销成功删除指定的cookie
//                .deleteCookies("cookie1", "cookie2")
//                // 自定义一些清理策略，和logoutSuccessHandler一样
//                .addLogoutHandler(new LogoutHandler() {
//                    @Override
//                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//
//                    }
//                })
                /** 自定义登录页配置 */
//            .loginPage("/myLogin.html")
//                .loginProcessingUrl("/auth/form").permitAll()
//                .failureHandler(new MyAuthenticationFailureHandler())
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
                /**
                 * 启用CORS支持
                 */
            .cors()
            .and()
            /**
             * sessionManagement 会话管理的四种策略
             * none
             * newSession
             * migrateSession 默认
             * changeSessionId
             */
            .sessionManagement()
                .sessionFixation().migrateSession()
                // 单个用户 最大会话数设置为1
                .maximumSessions(1)
                // 阻止新会话登录，默认为false
//                .maxSessionsPreventsLogin(true)
                // 设置超时后的自定义策略
//                .invalidSessionStrategy(new MyInvalidSessionStrategy())
                // 使用session提供的会话注册表
                .sessionRegistry(redisSessionRegistry)
        ;
        /**
         * 使用过滤器验证 验证码
         */
//        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
