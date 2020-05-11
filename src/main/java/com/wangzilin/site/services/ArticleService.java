package com.***REMOVED***.site.services;

import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.util.QueryPage;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:00 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface ArticleService {

    /**
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 查询文章
     * @Date 11:22 AM 5/11/2020
     * @Param [article, page, limit]
     **/
    List<Article> list(Article article, QueryPage queryPage);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 分页列出文章
     * @Date 11:25 AM 5/11/2020
     * @Param [queryPage]
     **/
    List<Article> list(QueryPage queryPage);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 添加
     * @Date 4:47 PM 5/7/2020
     * @Param [article]
     **/
    void add(Article article);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 删改
     * @Date 4:36 PM 5/7/2020
     * @Param [article]
     **/
    void update(Article article);


    void delete(Long id);

}
