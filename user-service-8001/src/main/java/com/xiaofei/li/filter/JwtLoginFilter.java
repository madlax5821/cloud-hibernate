package com.xiaofei.li.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaofei.li.service.jwtService.JwtTokenService;
import com.xiaofei.li.service.jwtService.JwtUser;
import com.xiaofei.li.vo.ResponseResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: xiaofei
 * Date: 2022-05-16, 13:42
 * Description:
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    public JwtLoginFilter(AuthenticationManager authenticationManager){
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtLoginFilter.attemptAuthentication");
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);
        username=username!=null?username:"";
        password=password!=null?password:"";

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        //Boolean ifRemember = Boolean.valueOf(request.getParameter("remember-me"));
        System.out.println("remember-me value: "+request.getParameter("remember-me"));
        String token = JwtTokenService.createToken(jwtUser, false);
        //Respond Bearer+token
        ResponseResult rs = ResponseResult.success(JwtTokenService.TOKEN_PREFIX+token);

        //set up response parameters
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(rs));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String errorMsg = "";
        if (failed instanceof BadCredentialsException){
            errorMsg = "username password not match";
        }else {
            errorMsg = failed.getMessage();
        }

        ResponseResult rs = ResponseResult.fail(errorMsg);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(rs))   ;
    }
}
