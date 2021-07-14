package com.hl.springsecurity.config;

import com.hl.springsecurity.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import sun.applet.Main;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    DataSource dataSource;

    @Autowired
    PersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                //自定义登陆页面
                .loginPage("/login.html")
                //自定义登陆逻辑
                .loginProcessingUrl("/login1")
                //登陆成功后跳转页面
                //.successForwardUrl("/toMain")
                //登陆成功的跳转逻辑
                .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))

                //登陆失败跳转页面
                //.failureForwardUrl("/toError")
                //自定义失败的跳转逻辑
                .failureHandler(new MyAuthenticationFailureHandler("/error.html"))
                //自定义登陆用户名参数
                .usernameParameter("username123")
                .passwordParameter("password123");



        //授权
        /**
         *    **：匹配0个或者多个目录
         *    *:匹配0个或者多个字符
         *    ?:匹配一个字符
         */
        http.authorizeRequests()
                //放行
                .antMatchers("/toLogin").permitAll()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error.html").permitAll()
                .antMatchers("/toDemo").permitAll()
                //根据权限匹配,区分大小写
                //.antMatchers("/main.html").hasAuthority("admiN")
                //包含其中一个即可
               // .antMatchers("/main1.html").hasAnyAuthority("admiN","normal")
                //根据角色匹配,不能以ROLE_开头，它会自动的添加上去
                //.antMatchers("/main1.html").hasRole("abc1")
                //基于ip地址访问
                //.antMatchers("/main1.html").hasIpAddress()


                //所有请求都会被认证（登陆）
                //.anyRequest().authenticated();
                //以下的方法等同于 .antMatchers("/main1.html").hasRole("abc1")
                //它的好处是可以自定义过滤逻辑
                .anyRequest().access("@myserviceImpl.hasPermission(request,authentication)");


        //处理异常
        http.exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler())
                ;

        http.rememberMe()
                //自定义登陆逻辑
                .userDetailsService(userService)
                //remember的参数
                .rememberMeParameter("rem")
                //默认两周
                .tokenValiditySeconds(3600)
                //自定义功能实现逻辑
                //.rememberMeServices()
                //指定存储位置
                .tokenRepository(persistentTokenRepository);

        http.logout()
                //退出登陆
                .logoutUrl("/logout")
                //退出登陆跳转成功
                .logoutSuccessUrl("/login.html");

       http.csrf().disable();
    }

    @Bean
    public PasswordEncoder pw(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(dataSource);
        //启动时是否创建 表，之后需要注释
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}
