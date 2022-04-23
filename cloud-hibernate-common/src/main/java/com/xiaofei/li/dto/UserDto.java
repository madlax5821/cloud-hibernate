package com.xiaofei.li.dto;

import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.Role;
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
    private String firstName;
    private String lastName;
    private Integer age;
    private Set set;


    public UserDto() {
    }

    public UserDto(Integer id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public UserDto(Integer id, String firstName, String lastName, Integer age, Set set) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.set = set;
    }
}
