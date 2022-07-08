package com.xiaofei.li.service;

import com.xiaofei.li.entity.Role;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 15:21
 * Description:
 */
@FeignClient ("role-service")
@LoadBalancerClient("role-service")
@Service
public interface RoleServiceConsumer {
    @GetMapping("/roles/{userId}")
    Set<Role> getRolesByUserId(@PathVariable Integer userId);
}
