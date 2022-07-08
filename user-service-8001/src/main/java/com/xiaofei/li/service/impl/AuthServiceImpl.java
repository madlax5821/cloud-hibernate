package com.xiaofei.li.service.impl;

import com.xiaofei.li.dao.PermissionDao;
import com.xiaofei.li.entity.Permission;
import com.xiaofei.li.entity.Role;
import com.xiaofei.li.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Author: xiaofei
 * Date: 2022-05-14, 21:18
 * Description:
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        if (authentication.getPrincipal()==null){
            return false;
        }
        
        Map<String, Collection<ConfigAttribute>> permissionMap = getPermissionMap();
        Collection<ConfigAttribute> configAttributes = null;
        
        for (String url:permissionMap.keySet()){
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
            if (matcher.matches(request)){
                configAttributes=permissionMap.get(url);
                break;
            }
        }
        if (configAttributes==null){
            return true;
        }
        
        for (ConfigAttribute configAttribute:configAttributes){
            String requiredRole = configAttribute.getAttribute();
            for (GrantedAuthority authority: authentication.getAuthorities()){
                String hasRole = authority.getAuthority();
                if (requiredRole.equals(hasRole)){
                    return true;
                }
            }
        }
        return false;
    }
    
    @PostConstruct
    private Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        Map<String, Collection<ConfigAttribute>> permissionMap = new HashMap<>();
        List<Permission> permissions = permissionDao.getAllPermissions();
        Collection<ConfigAttribute> configAttributes;
        
        for (Permission permission:permissions){
            String url = permission.getPermissionUrl();
            Set<Role> rolesPerUrl = permissionDao.getRolesByPermissionId(permission.getId());
            configAttributes=new ArrayList<>();
            for (Role role:rolesPerUrl){
                configAttributes.add((ConfigAttribute) role::getRoleName);
            }
            permissionMap.put(url,configAttributes);
        }
        return permissionMap;
    }
}




