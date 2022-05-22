package com.xiaofei.li.dto;

import com.xiaofei.li.dao.UserDao;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.Role;
import com.xiaofei.li.entity.User;
import lombok.Data;

import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 13:39
 * Description:
 */
@Data
public class UserDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;
    private Set set;


    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        if (user.getFullName()==null){
            this.firstName=null;
            this.lastName=null;
        }else {
            this.firstName = user.getFullName().getFirstName();
            this.lastName = user.getFullName().getLastName();
        }
        this.age = user.getAge();
    }

    public UserDto(User user, Set set) {
        this.id = user.getId();
        this.email = user.getEmail();
        if (user.getFullName()==null){
            this.firstName=null;
            this.lastName=null;
        }else {
            this.firstName = user.getFullName().getFirstName();
            this.lastName = user.getFullName().getLastName();
        }
        this.age = user.getAge();
        this.set = set;
    }
}
