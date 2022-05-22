package com.xiaofei.li.controller;

import com.xiaofei.li.dto.UserDto;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.User;
import com.xiaofei.li.service.AccountServiceConsumer;
import com.xiaofei.li.service.impl.UserServiceImpl;
import com.xiaofei.li.service.jwtService.JwtTokenService;
import com.xiaofei.li.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
    @PatchMapping("/profile")
    public ResponseResult updateUserProfile(HttpServletRequest request, @RequestBody User user){
        try {
            String token = request.getHeader(JwtTokenService.TOKEN_HEADER);
            UserDto userDto = userService.updateProfile(user, token);
            return ResponseResult.success(userDto);
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }
    
    
    @GetMapping("/profile")
    public ResponseResult displayUserProfile(HttpServletRequest request){
        String token = request.getHeader(JwtTokenService.TOKEN_HEADER);
        try {
            UserDto userDto = userService.displayUserProfile(token);
            return ResponseResult.success(userDto);
        }catch (Exception e){
            return ResponseResult.fail("not authorized to browse this page...");
        }
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        try {
            String token = userService.register(user);
            return ResponseResult.success(JwtTokenService.TOKEN_PREFIX+token);
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }
    
    @PostMapping("/profile/account")
    public ResponseResult addAccount(@RequestBody Account account, HttpServletRequest request){
        try {
            account = userService.addAccount(account, request);
            return ResponseResult.success(account);
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }
    
    @DeleteMapping("/profile/account")
    public ResponseResult deleteAccount(@RequestBody Account account, HttpServletRequest req){
        String token = req.getHeader(JwtTokenService.TOKEN_HEADER);
        try {
            userService.deleteAccountWithInUser(account,token);
            return ResponseResult.success();
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }
}
