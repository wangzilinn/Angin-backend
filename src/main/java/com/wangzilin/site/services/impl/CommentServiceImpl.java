package com.***REMOVED***.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.***REMOVED***.site.dao.CommentMapper;
import com.***REMOVED***.site.model.blog.Comment;
import com.***REMOVED***.site.services.CommentService;
import com.***REMOVED***.site.util.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:23 PM 5/8/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * @param queryPage
     * @return java.util.List<com.***REMOVED***.site.model.blog.Comment>
     * @Author ***REMOVED***
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
     * @return java.util.List<com.***REMOVED***.site.model.blog.Comment>
     * @Author ***REMOVED***
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
     * @return java.util.List<com.***REMOVED***.site.model.blog.Comment>
     * @Author ***REMOVED***
     * @Description 根据文章列出评论
     * @Date 3:57 PM 5/11/2020
     * @Param [id]
     */
    @Override
    public List<Comment> listByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public void add(Comment comment) {
        commentMapper.insert(comment);
    }


    @Override
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }
}
