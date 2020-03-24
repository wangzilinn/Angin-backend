package com.***REMOVED***.site.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserForAuth implements UserDetails {

    private String userId;
    private String password;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialExpired;
    private boolean enabled;
    private Set<GrantedAuthority> authorities;


    //把从数据库读取的用户对象转换为为用户验证的对象.
    public UserForAuth(UserProfile userProfile) {
        this(userProfile.getUserId(), userProfile.getPassword());
    }

    public UserForAuth(String userId, String password) {

        //这里将所有用户的全设为USER
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));//ROLE前缀必须

        this.userId = userId;
        this.password = password;

        this.authorities = authorities;

        this.accountExpired = false;
        this.accountLocked = false;
        this.credentialExpired = false;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getAuthoritiesString() {
        String authoritiesString = "";
        //TODO
//        String.join(",", )
    }
}
