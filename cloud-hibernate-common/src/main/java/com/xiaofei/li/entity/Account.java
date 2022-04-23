package com.xiaofei.li.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 13:46
 * Description:
 */
@Entity
@Table (name = "t_account")
public class Account implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column (name = "account_name")
    private String accountName;
    @Column (name = "account_desc")
    private String accountDesc;
    @ManyToOne
    @JoinColumn (name = "user_id")
    @JsonIgnore
    private User user;

    public Account() {
    }
    public Account(Integer id, String accountName, String accountDesc) {
        this.id = id;
        this.accountName = accountName;
        this.accountDesc = accountDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountDesc() {
        return accountDesc;
    }

    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", accountDesc='" + accountDesc + '\'' +
                '}';
    }
}
