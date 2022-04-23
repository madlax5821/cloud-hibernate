package com.xiaofei.li.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 13:54
 * Description:
 */
@Entity
@Table (name = "t_role")
public class Role implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column (name = "role_name")
    private String roleName;
    @ManyToMany (mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable (name = "t_role_permission",
                joinColumns = @JoinColumn (name = "role_id"),
                inverseJoinColumns = @JoinColumn (name = "permission_id"))
    @JsonIgnore
    private Set<Permission> permissions;

    public Role() {
    }

    public Role(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public void addPermission(Permission permission){
        this.getPermissions().add(permission);
        permission.getRoles().add(this);
    }

    public void removePermission(Permission permission){
        permission.getRoles().remove(this);
        this.getPermissions().remove(permission);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        if (users==null){
            users=new HashSet<>();
        }
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        if (permissions==null){
            permissions=new HashSet<>();
        }
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

//    @Override
//    public String toString() {
//        return "Role{" +
//                "id=" + id +
//                ", roleName='" + roleName + '\'' +
//                ", permissions=" + permissions +
//                '}';
//    }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
