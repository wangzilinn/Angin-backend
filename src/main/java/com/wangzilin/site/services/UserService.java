package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.UserDAO;
import com.***REMOVED***.site.model.SignRequest;
import com.***REMOVED***.site.model.user.UserForAuth;
import com.***REMOVED***.site.model.user.UserProfile;
import com.***REMOVED***.site.util.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserService.class);

    public void addUser(SignRequest signRequest) {
        this.addUser(new UserProfile(signRequest.getUserId(), signRequest.getPassword()));
    }

    //传入从网页来的json
    public void addUser(UserProfile userProfile) {
        userDAO.addUser(userProfile);
    }


    @Override
    public UserForAuth loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = userDAO.findUser(username);
        //将从数据库获取的user转为专为用户验证的user
        return new UserForAuth(userProfile);
    }

    public String signIn(String userId, String password) {
        // 认证用户，认证失败抛出异常，由JwtAuthError的commence类返回401
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userId, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);//这步在内部调用了UserService
        // 来验证用户是否存在xxxxxx
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
