package com.xiaofei.li.service;

import com.xiaofei.li.entity.Permission;
import com.xiaofei.li.entity.Role;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-05-14, 21:34
 * Description:
 */
@Service
@FeignClient ("permission-service")
@LoadBalancerClient ("permission-service")
public interface PermissionServiceConsumer {
    @GetMapping("/permissions/getAllPermissions")
    List<Permission> getAllPermissions();
    @GetMapping("/permissions/getRolesByPermissionId/{id}")
    Set<Role> getRolesByPermissionId(@PathVariable Integer id);
}
