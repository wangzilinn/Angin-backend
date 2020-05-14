package com.wangzilin.site.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangzilin.site.model.blog.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:23 PM 5/8/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
