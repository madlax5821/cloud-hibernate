package com.xiaofei.li.service;

import com.xiaofei.li.dao.UserDao;
import com.xiaofei.li.dto.UserDto;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.User;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 12:08
 * Description:
 */
@Service
public class UserService {
    @Autowired
    AccountServiceConsumer accountServiceConsumer;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleServiceConsumer roleServiceConsumer;

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
}
