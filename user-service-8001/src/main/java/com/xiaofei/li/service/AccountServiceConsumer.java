package com.xiaofei.li.service;

import com.xiaofei.li.entity.Account;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 12:04
 * Description:
 */
@Service
@FeignClient ("account-service")
@LoadBalancerClient("account-service")
public interface AccountServiceConsumer {
    @GetMapping("/accounts/{userId}")
    Set<Account> getAccountsByUserId(@PathVariable Integer userId);
}
