package com.xiaofei.li.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 14:11
 * Description:
 */
@Entity
@Table (name = "t_idCard")
public class IDCard implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column (name = "id_num")
    private String IDNum;
    @OneToOne (mappedBy = "idCard")
    @JsonIgnore
    private User user;

    public IDCard() {
    }

    public IDCard(Integer id, String IDNum) {
        this.id = id;
        this.IDNum = IDNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIDNum() {
        return IDNum;
    }

    public void setIDNum(String IDNum) {
        this.IDNum = IDNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "IDCard{" +
                "id=" + id +
                ", IDNum='" + IDNum + '\'' +
                '}';
    }
}
