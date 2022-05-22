package com.xiaofei.li.service;

import com.xiaofei.li.dao.AccountDao;
import com.xiaofei.li.dao.UserDao;
import com.xiaofei.li.dto.AccountDto;
import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 13:54
 * Description:
 */
@Service
public class AccountServiceImpl{
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private AccountDao accountDao;

    public Set<Account> getAccountByUserId(Integer userId){
        return accountDao.getAccountsByUserId(userId);

    }
    
    public void deleteAccount(Account account, Integer userId){
        accountDao.deleteAccountWithInUser(account,userId);
    }

    public Account addAccount(Account account, Integer userId) {
        accountDao.saveAccount(account,userId);
        System.out.println(account);
        return account;
    }
}
