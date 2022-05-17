package com.xiaofei.li.service.jwtService;

import com.xiaofei.li.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: xiaofei
 * Date: 2022-05-16, 0:35
 * Description:
 */
public class JwtUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private List<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    public JwtUser(User user, String password, List<? extends GrantedAuthority> authorities) {
        this.id = user.getId();
        this.username = user.getFullName().getFirstName();
        this.password = password;
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities==null){
            authorities=new ArrayList<>();
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
