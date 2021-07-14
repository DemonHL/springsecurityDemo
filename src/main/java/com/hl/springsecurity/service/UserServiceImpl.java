package com.hl.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        if(!"admin".equals(username)){
            throw  new UsernameNotFoundException("用户名密码错误");
        }
        String password = pw.encode("123456");

        //角色需要加上ROLE_开头
        return new User("admin",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc,/main1.html"));
    }


}
