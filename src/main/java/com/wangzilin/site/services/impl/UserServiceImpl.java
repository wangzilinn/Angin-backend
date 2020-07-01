package com.wangzilin.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangzilin.site.dao.UserMapper;
import com.wangzilin.site.model.user.User;
import com.wangzilin.site.requset.GithubInfoFeignClient;
import com.wangzilin.site.services.UserService;
import com.wangzilin.site.util.BeanUtil;
import com.wangzilin.site.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 这个类专门负责处理用户相关
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    final private UserMapper userMapper;
    final private GithubInfoFeignClient githubInfoFeignClient;
    final private ObjectMapper objectMapper;

    public UserServiceImpl(UserMapper userMapper, GithubInfoFeignClient githubInfoFeignClient,
                           ObjectMapper objectMapper) {
        this.userMapper = userMapper;
        this.githubInfoFeignClient = githubInfoFeignClient;
        this.objectMapper = objectMapper;
    }

    /**
     * 根据Name查询用户数据
     *
     * @param username
     * @return
     */
    @Override
    public User findByName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        List<User> list = userMapper.selectList(queryWrapper);
        log.debug(list.toString());
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 新增
     *
     * @param user
     */
    @Override
    public void add(User user) {
        //对用户密码进行加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userMapper.insert(user);
    }

    /**
     * 更新
     *
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public User auth(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication =
                ((AuthenticationManager) BeanUtil.getBean("authenticationMangerBean")).authenticate(authenticationToken);
        //这部在内部调用了本Service来验证用户是否存在
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //如果认证通过,则再次访问数据库,取出用户
        final User user = findByName(username);
        String token = JwtUtil.generateToken(user);
        user.setToken(token);
        return user;
    }

    @Override
    public Map<String, ?> getGithubInfo(String username) throws JsonProcessingException {
        return githubInfoFeignClient.info(username);
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByName(username);
    }

}
