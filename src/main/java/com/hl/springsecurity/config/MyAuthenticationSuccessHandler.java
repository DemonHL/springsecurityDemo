package com.hl.springsecurity.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }


    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Authorities==============?"+authentication.getAuthorities());
        System.out.println("getCredentials=============>"+authentication.getCredentials());
        System.out.println("getDetails=============>"+authentication.getDetails());
        System.out.println("getPrincipal=============>"+authentication.getPrincipal());
        response.sendRedirect(url);
    }
}
