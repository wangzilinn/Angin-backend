package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.user.User;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 12:13 AM 5/9/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
//TODO:准备合并完User一起实现
public interface UserService extends IService<User> {
    /**
     * 根据Name查询用户数据
     *
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 新增
     *
     * @param User
     */
    void add(User User);

    /**
     * 更新
     *
     * @param User
     */
    void update(User User);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);
}
