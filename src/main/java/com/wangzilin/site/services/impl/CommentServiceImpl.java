package com.wangzilin.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangzilin.site.dao.CommentMapper;
import com.wangzilin.site.model.DTO.QueryPage;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Comment;
import com.wangzilin.site.services.CommentService;
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
     * @param queryPage 用于查询的分页信息
     * @return 被包裹在Page中的结果
     */
    @Override
    public Response.Page<Comment> list(QueryPage queryPage) {
        IPage<Comment> page = new Page<>(queryPage.getPageForMongoDB(), queryPage.getLimit());
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<Comment> commentIPage = commentMapper.selectPage(page, queryWrapper);

        List<Comment> records = commentIPage.getRecords();
        long total = commentIPage.getTotal();

        return new Response.Page<>(records, queryPage, total);
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
    public Response.Page<Comment> listAbout(QueryPage queryPage) {
        IPage<Comment> page = new Page<>(queryPage.getPageForMongoDB(), queryPage.getLimit());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, null);
        IPage<Comment> commentIPage = commentMapper.selectPage(page, queryWrapper);

        List<Comment> records = commentIPage.getRecords();
        long total = commentIPage.getTotal();

        return new Response.Page<>(records, queryPage, total);
    }

    /**
     * @param queryPage
     * @param id
     * @return java.util.List<com.wangzilin.site.model.blog.Comment>
     * @Author wangzilin
     * @Description 根据文章列出评论
     * @Date 3:57 PM 5/11/2020
     * @Param [id]
     */
    @Override
    public Response.Page<Comment> listByArticleId(QueryPage queryPage, String id) {
        Page<Comment> page = new Page<>(queryPage.getPageForMongoDB(), queryPage.getLimit());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.orderByDesc(Comment::getId);
        IPage<Comment> commentIPage = commentMapper.selectPage(page, queryWrapper);

        List<Comment> records = commentIPage.getRecords();
        long total = commentIPage.getTotal();
        return new Response.Page<>(records, queryPage, total);
    }

    @Override
    public void add(Comment comment) {
        commentMapper.insert(comment);
    }


    @Override
    public void delete(String id) {
        commentMapper.deleteById(id);
    }

    @Override
    public void deleteByArticleId(String id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        commentMapper.delete(queryWrapper);
    }
}
