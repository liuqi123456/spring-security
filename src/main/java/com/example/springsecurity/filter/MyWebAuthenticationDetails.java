package com.example.springsecurity.filter;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean imageCodeIsRight;

    public boolean getImageCodeIsRight(){
        return this.imageCodeIsRight;
    }

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String imageCode = request.getParameter("captcha");
        String savedImageCode = (String) request.getSession().getAttribute("captcha");
        if (!StringUtils.isEmpty(savedImageCode)) {
            request.getSession().removeAttribute("captcha");
            if (!StringUtils.isEmpty(imageCode) && imageCode.equalsIgnoreCase(savedImageCode)) {
                this.imageCodeIsRight = true;
            }
        }
    }

}
