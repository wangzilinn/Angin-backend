package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.blog.ArchivesWithArticle;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.util.QueryPage;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:00 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface ArticleService extends IService<Article> {

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 获得最新的文章
     * @Date 4:27 PM 5/7/2020
     * @Param []
     **/
    List<Article> findAll();

    /**
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 分页查询
     * @Date 4:34 PM 5/7/2020
     * @Param [article, queryPage]
     **/
    IPage<Article> list(Article article, QueryPage queryPage);

    /**
     * @return com.***REMOVED***.site.model.blog.Article
     * @Author ***REMOVED***
     * @Description 根据Id查询
     * @Date 4:35 PM 5/7/2020
     * @Param [id]
     **/
    Article findById(Long id);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 删改
     * @Date 4:36 PM 5/7/2020
     * @Param [article]
     **/
    void update(Article article);

    void delete(Long id);

    /**
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 分页查询, 为前端服务
     * @Date 4:38 PM 5/7/2020
     * @Param [queryPage]
     **/
    IPage<Article> findByPageForSite(QueryPage queryPage);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 根据分类查询文章
     * @Date 4:40 PM 5/7/2020
     * @Param [category]
     **/
    List<Article> findByCategory(String category);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.ArchivesWithArticle>
     * @Author ***REMOVED***
     * @Description 查询按照时间归档的整合数据，
     * 格式：[{"2020-01", [{article},{article}...]}, {"2020-02", [{article}, {article}...]}]
     * @Date 4:46 PM 5/7/2020
     * @Param []
     **/
    List<ArchivesWithArticle> findArchives();

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 添加
     * @Date 4:47 PM 5/7/2020
     * @Param [article]
     **/
    void add(Article article);


}
