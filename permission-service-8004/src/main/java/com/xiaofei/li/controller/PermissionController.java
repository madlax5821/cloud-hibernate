package com.xiaofei.li.controller;

import com.xiaofei.li.entity.Permission;
import com.xiaofei.li.entity.Role;
import com.xiaofei.li.service.PermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 15:44
 * Description:
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionServiceImpl permissionService;

    @GetMapping("/getAllPermissions")
    public List<Permission> getAllPermissions(){
        return permissionService.getAllPermissions();
    }

    @GetMapping("/getRolesByPermissionUrl")
    public Set<Role> getRolesByPermissionUrl(String permissionUrl){
        return permissionService.getRolesByPermissionUrl(permissionUrl);
    }

    @GetMapping("/getRolesByPermissionId/{id}")
    public Set<Role> getRolesByPermissionId(@PathVariable Integer id){
        return permissionService.getRolesByPermissionId(id);
    }
}
