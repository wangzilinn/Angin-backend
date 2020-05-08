package com.***REMOVED***.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.***REMOVED***.site.constants.BlogConstant;
import com.***REMOVED***.site.mapper.CommentMapper;
import com.***REMOVED***.site.model.blog.Comment;
import com.***REMOVED***.site.model.blog.CommentTree;
import com.***REMOVED***.site.services.CommentService;
import com.***REMOVED***.site.util.CommentTreeUtil;
import com.***REMOVED***.site.util.QueryPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Comment> findAll() {
        return commentMapper.findAll(BlogConstant.DEFAULT_RELEASE_STATUS, new QueryPage(0,
                BlogConstant.COMMENT_PAGE_LIMIT));
    }

    @Override
    public IPage<Comment> list(Comment comment, QueryPage queryPage) {
        IPage<Comment> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Comment::getId);
        queryWrapper.like(StringUtils.isNotBlank(comment.getName()), Comment::getName, comment.getName());
        queryWrapper.like(StringUtils.isNotBlank(comment.getUrl()), Comment::getUrl, comment.getUrl());
        return commentMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Map<String, Object> findCommentsList(QueryPage queryPage, String articleId, int sort) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(articleId), Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getSort, sort);
        queryWrapper.orderByDesc(Comment::getId);
        //这里暂时先采用：先查询所有、再分页的方式
        List<Comment> list = commentMapper.selectList(queryWrapper);
        List<CommentTree<Comment>> CommentTrees = new ArrayList<>();
        list.forEach(c -> {
            CommentTree<Comment> CommentTree = new CommentTree<>();
            CommentTree.setId(c.getId());
            CommentTree.setPId(c.getPId());
            CommentTree.setAId(c.getArticleId());
            CommentTree.setContent(c.getContent());
            CommentTree.setName(c.getName());
            CommentTree.setTarget(c.getCName());
            CommentTree.setUrl(c.getUrl());
            CommentTree.setDevice(c.getDevice());
            CommentTree.setTime(c.getTime());
            CommentTree.setSort(c.getSort());
            CommentTrees.add(CommentTree);
        });
        Map<String, Object> map = new HashMap<>();
        try {
            List<CommentTree<Comment>> CommentTreeList = CommentTreeUtil.build(CommentTrees);

            if (CommentTreeList.size() == 0) {
                map.put("rows", new ArrayList<>());
            } else {
                int start = (queryPage.getPage() - 1) * queryPage.getLimit();
                int end = queryPage.getPage() * queryPage.getLimit();
                if (queryPage.getPage() * queryPage.getLimit() >= CommentTreeList.size()) {
                    end = CommentTreeList.size();
                }
                map.put("rows", CommentTreeList.subList(start, end));
            }
            map.put("count", list.size());
            map.put("total", CommentTreeList.size());
            map.put("current", queryPage.getPage());
            map.put("pages", (int) Math.ceil((double) CommentTreeList.size() / (double) queryPage.getLimit()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public int findCountByArticle(Long articleId) {
        IPage<Comment> page = new Page<>(0, 8);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        return commentMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional
    public void add(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        commentMapper.updateById(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }
}
