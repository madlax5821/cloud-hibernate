package com.xiaofei.li.config;

import com.xiaofei.li.filter.CustomFilter;
import com.xiaofei.li.filter.JwtAuthFilter;
import com.xiaofei.li.filter.JwtLoginFilter;
import com.xiaofei.li.service.AuthService;
import com.xiaofei.li.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Author: xiaofei
 * Date: 2022-05-04, 11:34
 * Description:
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity (prePostEnabled = true)
public class HttpWebServiceSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomFilter customFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.   //httpBasic().and().
                //formLogin().loginPage("/login").
                csrf().disable().
                authorizeRequests().
                antMatchers("/js/**","/images/**","/css/**").permitAll().//static resources
                antMatchers("/login","/register").permitAll().
                //anyRequest().authenticated();
                anyRequest().access("@authServiceImpl.canAccess(request,authentication)").
                and().
                addFilterAt(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class).
                addFilterAt(new JwtAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class).
                addFilterAfter(customFilter,UsernamePasswordAuthenticationFilter.class).
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).maximumSessions(1);
    }
}
