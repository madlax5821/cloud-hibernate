package com.xiaofei.li.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Author: xiaofei
 * Date: 2022-05-18, 17:22
 * Description:
 */
// alternative pattern to make sure there is always 1 filter running is to extend OncePerRequestFilter, same bottom logic
public class CustomFilter extends GenericFilter {
    
    private static final String FILTER_APPLIED =  "custom filter already be invoked";
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (servletRequest.getAttribute(FILTER_APPLIED)!=null){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        servletRequest.setAttribute(FILTER_APPLIED,true);
        System.out.println("CustomFilter.doFilter: "+request.getRequestURI()+"chain class: "+filterChain.getClass());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
