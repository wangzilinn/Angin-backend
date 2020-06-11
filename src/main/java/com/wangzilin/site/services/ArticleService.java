package com.wangzilin.site.services;

import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Article;
import com.wangzilin.site.model.blog.Category;
import com.wangzilin.site.model.blog.Tag;
import com.wangzilin.site.util.QueryPage;

import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:00 PM 5/6/2020
 * @Modified By:wangzilinn@gmail.com
 */
public interface ArticleService {

    /**
     * @return void
     * @Author wangzilin
     * @Description 添加
     * @Date 4:47 PM 5/7/2020
     * @Param [article]
     **/

    void addArticle(Article article);

    void deleteArticle(String id);

    /**
     * @return void
     * @Author wangzilin
     * @Description 删改
     * @Date 4:36 PM 5/7/2020
     * @Param [article]
     **/
    void updateArticle(Article article);

    /**
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
     * @Description 根据标题查询文章
     * @Date 1:05 PM 5/11/2020
     * @Param [title, queryPage]
     **/
    Response.Page<Article> listArticleByTitle(String title, QueryPage queryPage);

    /**
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
     * @Description 根据分类查询文章
     * @Date 1:07 PM 5/11/2020
     * @Param [category, queryPage]
     **/
    Response.Page<Article> listArticleByCategory(String category, QueryPage queryPage);

    /**
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
     * @Description 根据tag查询文章
     * @Date 3:20 PM 5/11/2020
     * @Param [tagName, queryPage]
     **/
    Response.Page<Article> listArticleByTag(String tagName, QueryPage queryPage);

    /**
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
     * @Description 分页列出文章
     * @Date 11:25 AM 5/11/2020
     * @Param [queryPage]
     **/
    Response.Page<Article> listArticle(QueryPage queryPage);

    Article findArticle(String id);

    interface CategoryService {
        void add(Category category);

        void delete(String name);

        void update(String from, String to);

        Response.Page<Category> list(QueryPage queryPage);

        List<Category> list();

        Category find(String name);
    }

    interface TagService {
        void add(Tag tag);

        void delete(String name);

        void update(String from, String to);

        Response.Page<Tag> list(QueryPage queryPage);

        List<Tag> list();

        Tag find(String name);
    }

}
