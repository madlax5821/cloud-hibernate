package com.xiaofei.li.service;

import com.xiaofei.li.dao.PermissionDao;
import com.xiaofei.li.entity.Permission;
import com.xiaofei.li.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 15:44
 * Description:
 */
@Service
public class PermissionServiceImpl {

    @Autowired
    private PermissionDao permissionDao;

    public List<Permission> getAllPermissions() {
        return permissionDao.getAllPermissions();
    }

    public Set<Role> getRolesByPermissionUrl(String permissionUrl) {
        return permissionDao.getRolesByPermissionUrl(permissionUrl);
    }

    public Set<Role> getRolesByPermissionId(Integer id) {
        return permissionDao.getRolesByPermissionId(id);
    }
}
