package com.xiaofei.li.filter;

import com.xiaofei.li.service.jwtService.JwtTokenService;
import com.xiaofei.li.service.jwtService.JwtUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: xiaofei
 * Date: 2022-05-16, 14:05
 * Description:
 */
public class JwtAuthFilter extends BasicAuthenticationFilter {
    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JwtTokenService.TOKEN_HEADER);
        if (token==null||!token.startsWith(JwtTokenService.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(createAuthentication(token));
        super.doFilterInternal(request,response,chain);
    }

    private Authentication createAuthentication(String token) {
        token = token.replaceAll(JwtTokenService.TOKEN_PREFIX,"");
        JwtUser jwtUser = JwtTokenService.parseTokenToJwtUser(token);
        return new UsernamePasswordAuthenticationToken(jwtUser.getUsername(),jwtUser.getPassword(),jwtUser.getAuthorities());
    }
}
