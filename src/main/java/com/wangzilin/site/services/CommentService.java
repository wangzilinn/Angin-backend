package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.blog.Comment;
import com.***REMOVED***.site.util.QueryPage;

import java.util.List;
import java.util.Map;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:19 PM 5/8/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查询最新的8条评论
     *
     * @return
     */
    List<Comment> findAll();

    /**
     * 分页查询
     *
     * @param comment
     * @param queryPage
     * @return
     */
    IPage<Comment> list(Comment comment, QueryPage queryPage);

    /**
     * 分页查询并过滤留言数据
     *
     * @param articleId 当前访问的文章ID
     * @param sort      分类，规定：sort=0表示文章详情页的评论信息；sort=1表示友链页的评论信息；sort=2表示关于我页的评论信息
     * @return
     */
    Map<String, Object> findCommentsList(QueryPage queryPage, String articleId, int sort);

    /**
     * 查询指定文章下的评论量
     *
     * @param articleId
     * @return
     */
    int findCountByArticle(Long articleId);

    /**
     * 新增
     *
     * @param comment
     */
    void add(Comment comment);

    /**
     * 更新
     *
     * @param comment
     */
    void update(Comment comment);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);
}
