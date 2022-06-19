package com.example.springsecurity.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 自定义 基本认证
 */
@Component("myAuthenticationProviderBase")
public class MyAuthenticationProviderBase extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        /**
         * TODO
         * 编写更多校验逻辑
         */
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(this.messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "密码不能为空"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            if (!presentedPassword.equals(userDetails.getPassword())) {
                throw new BadCredentialsException(this.messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials", "密码错误"));
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return userDetailsService.loadUserByUsername(username);
    }
}
