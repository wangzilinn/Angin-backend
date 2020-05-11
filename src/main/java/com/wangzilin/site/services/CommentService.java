package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.blog.Comment;
import com.***REMOVED***.site.util.QueryPage;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:19 PM 5/8/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface CommentService extends IService<Comment> {


    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Comment>
     * @Author ***REMOVED***
     * @Description 分页列出所有comment
     * @Date 3:56 PM 5/11/2020
     * @Param [queryPage]
     **/
    List<Comment> list(QueryPage queryPage);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Comment>
     * @Author ***REMOVED***
     * @Description 分页列出about评论
     * @Date 3:56 PM 5/11/2020
     * @Param [queryPage]
     **/
    List<Comment> listAbout(QueryPage queryPage);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Comment>
     * @Author ***REMOVED***
     * @Description 根据文章列出评论
     * @Date 3:57 PM 5/11/2020
     * @Param [id]
     **/
    List<Comment> listByArticleId(Long id);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 添加评论
     * @Date 3:57 PM 5/11/2020
     * @Param [comment]
     **/
    void add(Comment comment);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 删除评论
     * @Date 3:57 PM 5/11/2020
     * @Param [id]
     **/
    void delete(Long id);

}
