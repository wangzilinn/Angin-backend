package com.***REMOVED***.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.***REMOVED***.site.mapper.ArticleTagMapper;
import com.***REMOVED***.site.model.blog.ArticleTag;
import com.***REMOVED***.site.model.blog.Tag;
import com.***REMOVED***.site.services.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 10:08 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    /**
     * @param articleTag
     * @return void
     * @Author ***REMOVED***
     * @Description 新增关联关系
     * @Date 10:03 PM 5/7/2020
     * @Param [articleTag]
     */
    @Override
    public void add(ArticleTag articleTag) {
        if (!exists(articleTag)) {
            articleTagMapper.insert(articleTag);
        }
    }

    private boolean exists(ArticleTag articleTag) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleTag.getArticleId());
        queryWrapper.eq(ArticleTag::getTagId, articleTag.getTagId());
        return articleTagMapper.selectList(queryWrapper).size() > 0;
    }

    @Override
    public List<Tag> findByArticleId(Long articleId) {
        return articleTagMapper.findByArticleId(articleId);
    }


    /**
     * @param id
     * @return void
     * @Author ***REMOVED***
     * @Description 根据文章id删除添加于其上的标签
     * @Date 10:07 PM 5/7/2020
     * @Param [id]
     */
    @Override
    public void deleteByArticleId(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        articleTagMapper.delete(queryWrapper);
    }

    @Override
    public void deleteByTagsId(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getTagId, id);
        articleTagMapper.delete(queryWrapper);
    }
}
