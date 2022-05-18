package com.xiaofei.li.controller;

import com.xiaofei.li.dto.UserDto;
import com.xiaofei.li.service.impl.UserServiceImpl;
import com.xiaofei.li.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 12:03
 * Description:
 */
@RestController
@RequestMapping ("/main")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/users/{userId}")
    public ResponseResult getUserByUserId(@PathVariable Integer userId){
        try {
            UserDto userDto = userService.findUserByUserId(userId);
            return ResponseResult.success(userDto);
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }
    @GetMapping("/accounts/{userId}")
    public ResponseResult retrieveAccountsInfoByUserId(@PathVariable Integer userId){
        UserDto userDto = userService.getAccountByUserId(userId);
        return ResponseResult.success(userDto);
    }

    @GetMapping("/roles/{userId}")
    public ResponseResult retrieveRolesByUserId(@PathVariable Integer userId){
        try {
            UserDto userDto = userService.getRolesByUserId(userId);
            return ResponseResult.success(userDto);
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseResult getAllUsers(){
        return ResponseResult.success(userService.getAllUsers());
    }
}
