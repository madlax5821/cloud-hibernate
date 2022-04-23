package com.xiaofei.li.controller;

import com.xiaofei.li.entity.Role;
import com.xiaofei.li.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 15:27
 * Description:
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping("/{userId}")
    public Set<Role> findRoleByUserId(@PathVariable Integer userId){
        return roleService.getRolesByUserId(userId);
    }
}
