package com.xiaofei.li.service.impl;

import com.xiaofei.li.dao.UserDao;
import com.xiaofei.li.dto.UserDto;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.Role;
import com.xiaofei.li.entity.User;
import com.xiaofei.li.service.AccountServiceConsumer;
import com.xiaofei.li.service.RoleServiceConsumer;
import com.xiaofei.li.service.UserService;
import com.xiaofei.li.service.jwtService.JwtUser;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 12:08
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AccountServiceConsumer accountServiceConsumer;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleServiceConsumer roleServiceConsumer;

    public List<UserDto> getAllUsers(){
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userDao.getAllUsers();
        for (User user:users){
            userDtos.add(new UserDto(
                    user.getId(),
                    user.getFullName().getFirstName(),
                    user.getFullName().getLastName(),
                    user.getAge()
            ));
        }
        return userDtos;
    }

    public UserDto getAccountByUserId(Integer userId){
        User user = userDao.getUserById(userId);
        return new UserDto(
                user.getId(),
                user.getFullName().getFirstName(),
                user.getFullName().getLastName(),
                user.getAge(),
                accountServiceConsumer.getAccountsByUserId(userId)
        );
    }

    public UserDto findUserByUserId(Integer userId) {
        User user = userDao.getUserById(userId);
        if (user==null){
            throw new UsernameNotFoundException("user does not exist");
        }
        return new UserDto(
                user.getId(),
                user.getFullName().getFirstName(),
                user.getFullName().getLastName(),
                user.getAge());
    }

    public UserDto getRolesByUserId(Integer userId){
        User user = userDao.getUserById(userId);
        return new UserDto(
                user.getId(),
                user.getFullName().getFirstName(),
                user.getFullName().getLastName(),
                user.getAge(),
                roleServiceConsumer.getRolesByUserId(userId)
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDao.getUserByUsername(username);
            if (user==null){
                throw new UsernameNotFoundException("user does not exist");
            }

            Integer id = user.getId();
            String password = user.getPassword();

            Set<Role> roles = roleServiceConsumer.getRolesByUserId(id);
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role:roles){
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
            //return new org.springframework.security.core.userdetails.User(username,password,authorities);
            return new JwtUser(user,password,authorities);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
