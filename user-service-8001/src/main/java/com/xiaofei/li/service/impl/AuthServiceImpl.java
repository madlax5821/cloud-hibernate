package com.xiaofei.li.service.impl;

import com.xiaofei.li.entity.Permission;
import com.xiaofei.li.entity.Role;
import com.xiaofei.li.service.AuthService;
import com.xiaofei.li.service.PermissionServiceConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private PermissionServiceConsumer permissionServiceConsumer;

    @Override
    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal==null){
            return false;
        }

        Map<String, Collection<ConfigAttribute>> permissionMap = getPermissionMap();

        Collection<ConfigAttribute> configAttributeCollection = null;

        for (String url:permissionMap.keySet()){
            AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(url);
            if (antPathRequestMatcher.matches(request)){
                System.out.println("url: "+url+"|| request: "+request.getRequestURI());
                configAttributeCollection=permissionMap.get(url);
                break;
            }
        }

        if (configAttributeCollection==null){
            return true;
        }

        for (ConfigAttribute configAttribute:configAttributeCollection){
            String roleName =configAttribute.getAttribute();
            for (GrantedAuthority authority: authentication.getAuthorities()){
                System.out.println("role name: "+authority.getAuthority());
                if (roleName.equals(authority.getAuthority())){
                    return true;
                }
            }
        }
        return false;
    }

    @PostConstruct
    private Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        Map<String, Collection<ConfigAttribute>> permissionMap = new HashMap<>();

        List<Permission> permissions = permissionServiceConsumer.getAllPermissions();

        Collection<ConfigAttribute> configAttributeCollection = null;

        for (Permission permission:permissions){
            configAttributeCollection=new ArrayList<>();
            String permissionUrl = permission.getPermissionUrl();
            Set<Role> roles = permissionServiceConsumer.getRolesByPermissionId(permission.getId());
            for (Role role:roles){
                configAttributeCollection.add((ConfigAttribute) role::getRoleName);
            }
            permissionMap.put(permissionUrl,configAttributeCollection);
        }
        return permissionMap;
    }
}
