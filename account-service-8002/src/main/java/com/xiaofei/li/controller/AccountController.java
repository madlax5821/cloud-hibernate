package com.xiaofei.li.controller;

import com.netflix.discovery.converters.Auto;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.service.AccountServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 13:52
 * Description:
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/{userId}")
    public Set<Account> getAccountByUserId(@PathVariable Integer userId){
        return accountService.getAccountByUserId(userId);
    }

}
