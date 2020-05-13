package com.***REMOVED***.site.services;

import com.***REMOVED***.site.model.DTO.Page;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.model.blog.Category;
import com.***REMOVED***.site.model.blog.Tag;
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
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 根据标题查询文章
     * @Date 1:05 PM 5/11/2020
     * @Param [title, queryPage]
     **/
    Page<Article> listArticleByTitle(String title, QueryPage queryPage);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 根据分类查询文章
     * @Date 1:07 PM 5/11/2020
     * @Param [category, queryPage]
     **/
    Page<Article> listArticleByCategory(String category, QueryPage queryPage);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 根据tag查询文章
     * @Date 3:20 PM 5/11/2020
     * @Param [tagName, queryPage]
     **/
    Page<Article> listArticleByTag(String tagName, QueryPage queryPage);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 分页列出文章
     * @Date 11:25 AM 5/11/2020
     * @Param [queryPage]
     **/
    Page<Article> listArticle(QueryPage queryPage);

    Article findArticle(String id);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 添加
     * @Date 4:47 PM 5/7/2020
     * @Param [article]
     **/
    void addArticle(Article article);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 删改
     * @Date 4:36 PM 5/7/2020
     * @Param [article]
     **/
    void updateArticle(Article article);


    void deleteArticle(String id);

    Page<Category> listCategory(QueryPage queryPage);

    List<Category> listCategory();

    void updateCategory(String categoryName);

    void deleteCategory(String categoryName);

    Page<Tag> listTag(QueryPage queryPage);

    List<Tag> listTag();

    void updateTag(String tagName);

    void deleteTag(String tagName);


}
