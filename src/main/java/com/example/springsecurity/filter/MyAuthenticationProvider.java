package com.example.springsecurity.filter;

import com.example.springsecurity.exception.VerificationCodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义 实现图形验证码认证
 */
@Component("myAuthenticationProvider")
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        /**
         * 实现图形验证码的校验逻辑
         */
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        if (!details.getImageCodeIsRight()) {
            throw new VerificationCodeException();
        }
        // 调用父类方法完成密码验证
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
