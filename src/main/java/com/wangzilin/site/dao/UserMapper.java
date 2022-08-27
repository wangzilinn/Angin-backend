package com.wangzilin.site.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangzilin.site.model.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 12:09 AM 5/9/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
