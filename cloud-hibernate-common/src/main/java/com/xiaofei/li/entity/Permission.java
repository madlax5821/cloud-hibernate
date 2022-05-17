package com.xiaofei.li.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 14:01
 * Description:
 */
@Entity
@Table (name = "t_permission")
public class Permission implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column (name = "permission_url")
    private String permissionUrl;
    @Column (name = "permission_desc")
    private String permissionDesc;
    @ManyToMany (mappedBy = "permissions")
    @JsonIgnore
    private Set<Role> roles;

    public Permission() {
    }

    public Permission(Integer id, String permissionUrl, String permissionDesc) {
        this.id = id;
        this.permissionUrl = permissionUrl;
        this.permissionDesc = permissionDesc;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionUrl='" + permissionUrl + '\'' +
                ", permissionDesc='" + permissionDesc + '\'' +
                '}';
    }
}
