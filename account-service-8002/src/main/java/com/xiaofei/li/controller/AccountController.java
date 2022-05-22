package com.xiaofei.li.controller;

import com.netflix.discovery.converters.Auto;
import com.xiaofei.li.dao.AccountDao;
import com.xiaofei.li.dto.AccountDto;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.service.AccountServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    
    @PostMapping("/account")
    public Account addAccount(@RequestBody Account account, @RequestParam(name = "userId") Integer userId){
        return accountService.addAccount(account,userId);
    }
    
    @DeleteMapping("/account")
    public void deleteAccount(@RequestBody AccountDto accountDto){
        accountService.deleteAccount(accountDto.getAccount(),accountDto.getUserId());
    }

}
