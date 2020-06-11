package com.wangzilin.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangzilin.site.dao.CommentMapper;
import com.wangzilin.site.model.blog.Comment;
import com.wangzilin.site.services.CommentService;
import com.wangzilin.site.util.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:23 PM 5/8/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * @param queryPage
     * @return java.util.List<com.wangzilin.site.model.blog.Comment>
     * @Author wangzilin
     * @Description 分页列出所有comment
     * @Date 3:56 PM 5/11/2020
     * @Param [queryPage]
     */
    @Override
    public List<Comment> list(QueryPage queryPage) {
        IPage<Comment> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        return commentMapper.selectPage(page, queryWrapper).getRecords();
    }

    /**
     * @param queryPage
     * @return java.util.List<com.wangzilin.site.model.blog.Comment>
     * @Author wangzilin
     * @Description 分页列出about评论
     * @Date 3:56 PM 5/11/2020
     * @Param [queryPage]
     */
    @Override
    public List<Comment> listAbout(QueryPage queryPage) {
        IPage<Comment> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, null);
        return commentMapper.selectPage(page, queryWrapper).getRecords();
    }

    /**
     * @param id
     * @return java.util.List<com.wangzilin.site.model.blog.Comment>
     * @Author wangzilin
     * @Description 根据文章列出评论
     * @Date 3:57 PM 5/11/2020
     * @Param [id]
     */
    @Override
    public List<Comment> listByArticleId(String id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public void add(Comment comment) {
        commentMapper.insert(comment);
    }


    @Override
    public void delete(String id) {
        commentMapper.deleteById(id);
    }
}
