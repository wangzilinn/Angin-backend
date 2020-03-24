package com.***REMOVED***.site.services;

import com.***REMOVED***.site.auth.AuthUserService;
import com.***REMOVED***.site.auth.JwtUtil;
import com.***REMOVED***.site.model.user.UserForAuth;
import com.***REMOVED***.site.model.user.UserProfile;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

//专门用于用户验证
@Service
public class AuthService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthService.class);

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, AuthUserService userDetailsService,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public UserProfile register(UserProfile userToAdd) {
        // TODO: 保存user到数据库
        return null;
    }

    public String login(String userId, String password) {
        // 认证用户，认证失败抛出异常，由JwtAuthError的commence类返回401
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userId, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);//这步在内部调用了UserService来验证用户是否存在
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 如果认证通过，再次访问数据库, 取出用户
        final UserForAuth userForAuth = userService.loadUserByUsername(userId);
        return jwtUtil.generateToken(userForAuth);
    }

    public String refresh(String oldToken) {
        String newToken = null;

        try {
            newToken = jwtUtil.refreshToken(oldToken);
        } catch (Exception e) {
            log.debug("异常详情", e);
            log.info("无效token");
        }
        return newToken;
    }
}
