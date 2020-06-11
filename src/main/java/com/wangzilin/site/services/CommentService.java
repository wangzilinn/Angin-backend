package com.wangzilin.site.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Comment;
import com.wangzilin.site.util.QueryPage;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:19 PM 5/8/2020
 * @Modified By:wangzilinn@gmail.com
 */
public interface CommentService extends IService<Comment> {


    /**
     * @return java.util.List<com.wangzilin.site.model.blog.Comment>
     * @Author wangzilin
     * @Description 分页列出所有comment
     * @Date 3:56 PM 5/11/2020
     * @Param [queryPage]
     **/
    Response.Page<Comment> list(QueryPage queryPage);

    /**
     * @return java.util.List<com.wangzilin.site.model.blog.Comment>
     * @Author wangzilin
     * @Description 分页列出about评论
     * @Date 3:56 PM 5/11/2020
     * @Param [queryPage]
     **/
    Response.Page<Comment> listAbout(QueryPage queryPage);

    /**
     * @param queryPage
     * @param id
     * @return java.util.List<com.wangzilin.site.model.blog.Comment>
     * @Author wangzilin
     * @Description 根据文章列出评论
     * @Date 3:57 PM 5/11/2020
     * @Param [id]
     */
    Response.Page<Comment> listByArticleId(QueryPage queryPage, String id);

    /**
     * @return void
     * @Author wangzilin
     * @Description 添加评论
     * @Date 3:57 PM 5/11/2020
     * @Param [comment]
     **/
    void add(Comment comment);

    /**
     * @return void
     * @Author wangzilin
     * @Description 删除评论
     * @Date 3:57 PM 5/11/2020
     * @Param [id]
     *
     * @param id*/
    void delete(String id);

}
