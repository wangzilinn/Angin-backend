package com.wangzilin.site.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangzilin.site.model.DTO.SimpleUserInfoRequest;
import com.wangzilin.site.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 12:13 AM 5/9/2020
 * @Modified By:wangzilinn@gmail.com
 */
//TODO:准备合并完User一起实现
public interface UserService extends IService<User>, UserDetailsService {
    /**
     * 根据Name查询用户数据
     *
     * @param username
     * @return
     */
    User findByName(String username);


    /**
     * 新用户注册
     * @param simpleUserInfoRequest 用户注册请求
     */
    void signUp(SimpleUserInfoRequest simpleUserInfoRequest);

    /**
     * 更新
     *
     * @param user
     */
    void update(User user);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    User auth(String username, String password);

    Map getGithubInfo(String username) throws JsonProcessingException;
}
