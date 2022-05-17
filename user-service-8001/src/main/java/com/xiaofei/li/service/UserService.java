package com.xiaofei.li.service;

import com.xiaofei.li.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Author: xiaofei
 * Date: 2022-05-14, 21:08
 * Description:
 */

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();
    UserDto findUserByUserId(Integer userId);
}
