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
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);
        
        username = username!=null?username:"";
        password = password!=null?password:"";
        
//        if (username.startsWith("kevin")){
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/json");
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                response.getWriter().write(mapper.writeValueAsString(ResponseResult.fail("too ugly to access")));
//                return null;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(request.getParameter("remember-me"));
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        Boolean ifRememberMe = Boolean.parseBoolean((String) authResult.getDetails());
        String token = JwtTokenService.generateToken(jwtUser, ifRememberMe);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(ResponseResult.success(JwtTokenService.TOKEN_PREFIX+token)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String errorMsg = "";
        if (failed instanceof BadCredentialsException){
            errorMsg = "username or password not match";
        }else {
            errorMsg = failed.getMessage();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(ResponseResult.fail(errorMsg)));
    }
}
