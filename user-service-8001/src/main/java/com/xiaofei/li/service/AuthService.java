package com.xiaofei.li.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: xiaofei
 * Date: 2022-05-14, 21:17
 * Description:
 */
public interface AuthService {
    boolean canAccess(HttpServletRequest request, Authentication authentication);
}
