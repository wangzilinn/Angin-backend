package com.***REMOVED***.site.services.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.***REMOVED***.site.mapper.ArticleMapper;
import com.***REMOVED***.site.model.blog.ArchivesWithArticle;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.services.*;
import com.***REMOVED***.site.util.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 10:56 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleTagService articleTagService;

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 获得最新的文章
     * @Date 4:27 PM 5/7/2020
     * @Param []
     **/
    @Override
    public List<Article> findAll() {
        return null;
    }

    /**
     * @param article
     * @param queryPage
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 分页查询
     * @Date 4:34 PM 5/7/2020
     * @Param [article, queryPage]
     */
    @Override
    public IPage<Article> list(Article article, QueryPage queryPage) {
        return null;
    }

    /**
     * @param id
     * @return com.***REMOVED***.site.model.blog.Article
     * @Author ***REMOVED***
     * @Description 根据Id查询
     * @Date 4:35 PM 5/7/2020
     * @Param [id]
     */
    @Override
    public Article findById(Long id) {
        return null;
    }

    /**
     * @param article
     * @return void
     * @Author ***REMOVED***
     * @Description 删改
     * @Date 4:36 PM 5/7/2020
     * @Param [article]
     */
    @Override
    public void update(Article article) {

    }

    @Override
    public void delete(Long id) {

    }

    /**
     * @param queryPage
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 分页查询, 为前端服务
     * @Date 4:38 PM 5/7/2020
     * @Param [queryPage]
     */
    @Override
    public IPage<Article> findByPageForSite(QueryPage queryPage) {
        return null;
    }

    /**
     * @param category
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 根据分类查询文章
     * @Date 4:40 PM 5/7/2020
     * @Param [category]
     */
    @Override
    public List<Article> findByCategory(String category) {
        return null;
    }

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.ArchivesWithArticle>
     * @Author ***REMOVED***
     * @Description 查询按照时间归档的整合数据，
     * 格式：[{"2020-01", [{article},{article}...]}, {"2020-02", [{article}, {article}...]}]
     * @Date 4:46 PM 5/7/2020
     * @Param []
     **/
    @Override
    public List<ArchivesWithArticle> findArchives() {
        return null;
    }

    /**
     * @param article
     * @return void
     * @Author ***REMOVED***
     * @Description 添加
     * @Date 4:47 PM 5/7/2020
     * @Param [article]
     */
    @Override
    public void add(Article article) {

    }
}
