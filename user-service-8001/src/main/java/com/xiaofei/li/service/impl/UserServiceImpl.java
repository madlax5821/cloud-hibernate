package com.xiaofei.li.service.impl;

import com.xiaofei.li.dao.RoleDao;
import com.xiaofei.li.dao.UserDao;
import com.xiaofei.li.dto.AccountDto;
import com.xiaofei.li.dto.UserDto;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.Role;
import com.xiaofei.li.entity.User;
import com.xiaofei.li.service.AccountServiceConsumer;
import com.xiaofei.li.service.RoleServiceConsumer;
import com.xiaofei.li.service.UserService;
import com.xiaofei.li.service.jwtService.JwtTokenService;
import com.xiaofei.li.service.jwtService.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 12:08
 * Description:
 */
@Service
@Transactional (propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private AccountServiceConsumer accountServiceConsumer;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleServiceConsumer roleServiceConsumer;
    @Autowired
    private PasswordEncoder encoder;
    
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<UserDto> getAllUsers(){
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userDao.getAllUsers();
        for (User user:users){
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDto getAccountByUserId(Integer userId){
        User user = userDao.getUserById(userId);
        return new UserDto(
                user,
                accountServiceConsumer.getAccountsByUserId(userId)
        );
    }
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDto findUserByUserId(Integer userId) {
        User user = userDao.getUserById(userId);
        if (user==null){
            throw new UsernameNotFoundException("user does not exist");
        }
        return new UserDto(user);
    }
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDto getRolesByUserId(Integer userId){
        try {
            User user = userDao.getUserById(userId);
            return new UserDto(
                    user,
                    roleServiceConsumer.getRolesByUserId(userId)
            );
        }catch (Exception e){
            throw new UsernameNotFoundException("USER NOT EXISTS!!!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userDao.getUserByEmail(email);
            if (user==null){
                throw new UsernameNotFoundException("user not exists");
            }
            Integer userId = user.getId();
            String password = user.getPassword();

            Set<Role> roles = roleServiceConsumer.getRolesByUserId(userId);

            List<GrantedAuthority> authorities = new ArrayList<>();

            for (Role role:roles){
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
            return new JwtUser(user,password,authorities);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String register(User user) throws Exception {
        User userInDataSource = userDao.getUserByEmail(user.getEmail());
        // determine if the email address already registered
        if  (userInDataSource!=null){
            throw new Exception("username cannot be the same");
        }
        //encrypt the raw password and then save the user in dataSource
        String encryptPass = encoder.encode(user.getPassword());
        user.setPassword(encryptPass);
        userDao.saveUser(user);
        
        JwtUser jwtUser = (JwtUser) loadUserByUsername(user.getEmail());
        String token = JwtTokenService.generateToken(jwtUser, false);
        return token;
    }

    public UserDto displayUserProfile(String token) {
        token = token.replaceAll(JwtTokenService.TOKEN_PREFIX,"");
        JwtUser jwtUser = JwtTokenService.parseTokenToJwtUser(token);
        return getAccountByUserId(jwtUser.getId());
    }

    public UserDto updateProfile(User user, String token) throws Exception {
        token=token.replaceAll(JwtTokenService.TOKEN_PREFIX,"");
        JwtUser jwtUser = JwtTokenService.parseTokenToJwtUser(token);
        user.setId(jwtUser.getId());
        try {
            userDao.updateUser(user);
            user.setEmail(jwtUser.getUsername());
            return new UserDto(user);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Account addAccount(Account account, HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenService.TOKEN_HEADER);
        token = token.replaceAll(JwtTokenService.TOKEN_PREFIX,"");
        JwtUser jwtUser = JwtTokenService.parseTokenToJwtUser(token);
        Integer userId = jwtUser.getId();
        try {
            return accountServiceConsumer.addAccount(account,userId);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    public void deleteAccountWithInUser(Account account, String token) throws Exception {
        token = token.replaceAll(JwtTokenService.TOKEN_PREFIX,"");
        JwtUser user = JwtTokenService.parseTokenToJwtUser(token);
        AccountDto accountDto = new AccountDto(account,user.getId());
        try {
            accountServiceConsumer.deleteAccount(accountDto);
        }catch (Exception e){
            throw new Exception("account doesn't exist");
        }
    }
}