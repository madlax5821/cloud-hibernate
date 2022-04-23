package com.xiaofei.li.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 13:40
 * Description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FullName {
    @Column (name = "first_name")
    private String firstName;
    @Column (name = "last_name")
    private String lastName;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "FullName{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
