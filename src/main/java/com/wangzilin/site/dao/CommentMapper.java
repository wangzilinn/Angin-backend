package com.***REMOVED***.site.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.***REMOVED***.site.model.blog.Comment;
import com.***REMOVED***.site.util.QueryPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:23 PM 5/8/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> findAll(@Param("state") String state, @Param("queryPage") QueryPage queryPage);
}
