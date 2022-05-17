package com.xiaofei.li.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 12:43
 * Description:
 */
@Entity
@Table (name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Embedded
    private FullName fullName;
    @Column
    private String password;
    @Column
    private Integer age;

    @OneToMany (fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Account> accounts;

    @ManyToMany (fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable (name = "t_user_role",
                joinColumns = @JoinColumn (name = "user_id"),
                inverseJoinColumns = @JoinColumn (name = "role_id"))
    private Set<Role> roles;

    @OneToOne (fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn (name = "card_id")
    private IDCard idCard;

    public User() {
    }

    public User(Integer id, FullName fullName, Integer age) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
    }

    public void addIDCard(IDCard idCard){
        this.setIdCard(idCard);
        idCard.setUser(this);
    }
    public void removeIDCard(){
        idCard.setUser(null);
        this.setIdCard(null);
    }

    public void addAccount(Account account){
        this.getAccounts().add(account);
        account.setUser(this);
    }

    public void removeAccount(Account account){
        account.setUser(null);
        this.getAccounts().remove(account);
    }

    public void addRole(Role role){
        this.getRoles().add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role){
        role.getUsers().remove(this);
        this.getRoles().remove(role);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Account> getAccounts() {
        if (accounts==null){
            accounts=new HashSet<>();
        }
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Role> getRoles() {
        if (roles==null){
            roles=new HashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public IDCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IDCard idCard) {
        this.idCard = idCard;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", fullName=" + fullName +
//                ", age=" + age +
//                ", accounts=" + accounts +
//                ", roles=" + roles +
//                ", idCard=" + idCard +
//                '}';
//    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName=" + fullName +
                ", age=" + age +
                '}';
    }
}
