package com.example.springsecurity.filter;

import com.example.springsecurity.exception.VerificationCodeException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用过滤器验证 验证码
 */
public class VerificationCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!"/auth/form".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            try {
                verificationCode(request);
                filterChain.doFilter(request, response);
            } catch (VerificationCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }

        }
    }

    public void verificationCode(HttpServletRequest request) throws VerificationCodeException {
        String requestCode = request.getParameter("captcha");
        String savedCode = (String) request.getSession().getAttribute("captcha");
        if (!StringUtils.isEmpty(savedCode)) {
            request.getSession().removeAttribute("captcha");
        }
        if (StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(savedCode) || !requestCode.equalsIgnoreCase(savedCode)) {
            throw new VerificationCodeException();
        }
    }
}
