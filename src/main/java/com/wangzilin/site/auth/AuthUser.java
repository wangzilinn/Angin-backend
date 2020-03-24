package com.***REMOVED***.site.auth;

import com.***REMOVED***.site.model.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

//这个user类专门给登陆用
public class AuthUser implements UserDetails {

    private static final long serialVersionUID = -2336372258701871345L;

    private UserProfile user;

    public AuthUser(UserProfile user) {
        this.setUser(user);
    }

    public static Collection<? extends GrantedAuthority> getAuthoritiesByRole(String role) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        List<String> roles = Arrays.asList(role.split(","));
        if (roles.contains("user")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));//ROLE前缀必须
        }
        if (roles.contains("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesByRole(getUser().getRole());
    }

    //提供密码
    @Override
    public String getPassword() {
        return getUser().getPassword();
    }

    //提供用户名
    @Override
    public String getUsername() {
        return getUser().getUserId();
    }

    //账户是否没过期, 如果账户过期则无法认证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账号是否没锁住，锁住的用户无法认证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否没过期，密码过期的用户无法认证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 用户是否使能，未使能的用户无法认证
    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}
