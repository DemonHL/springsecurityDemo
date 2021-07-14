package com.hl.springsecurity.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
public class MyserviceImpl {

    //登陆成功后springsecurity会放入一个Authentication
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){
        String requestURI = request.getRequestURI();
        System.out.println("--------"+requestURI);
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails=(UserDetails)principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
           return authorities.contains(new SimpleGrantedAuthority(requestURI));
        }
        return false;
    }

}
