package com.xiaofei.li.dto;

import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.User;

/**
 * Author: xiaofei
 * Date: 2022-05-21, 21:22
 * Description:
 */
public class AccountDto {
    private Account account;
    private Integer userId;

    public AccountDto() {
    }

    public AccountDto(Account account, Integer userId) {
        this.account = account;
        this.userId = userId;
    }

    public Account getAccount() {
        return account;
    }

    public Integer getUserId() {
        return userId;
    }
}
