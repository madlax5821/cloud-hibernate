package com.xiaofei.li.service;

import com.xiaofei.li.dao.RoleDao;
import com.xiaofei.li.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 15:26
 * Description:
 */
@Service
public class RoleServiceImpl {
    @Autowired
    private RoleDao roleDao;

    public Set<Role> getRolesByUserId(Integer userId){
        return roleDao.getRoleByUserId(userId);
    }
}
