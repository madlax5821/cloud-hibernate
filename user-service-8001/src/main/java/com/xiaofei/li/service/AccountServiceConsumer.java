package com.xiaofei.li.service;

import com.xiaofei.li.dto.AccountDto;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.User;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/accounts/account")
    Account addAccount(@RequestBody Account account, @RequestParam (name = "userId")Integer userId);
    @DeleteMapping("/accounts/account")
    void deleteAccount(@RequestBody AccountDto accountDto);
}
