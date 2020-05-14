package com.***REMOVED***.site.services.impl;

import com.***REMOVED***.site.dao.ArticleDAO;
import com.***REMOVED***.site.dao.CategoryDAO;
import com.***REMOVED***.site.dao.TagDAO;
import com.***REMOVED***.site.model.DTO.Page;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.model.blog.Category;
import com.***REMOVED***.site.model.blog.Tag;
import com.***REMOVED***.site.services.ArticleService;
import com.***REMOVED***.site.util.QueryPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 10:56 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    ArticleDAO articleDAO;
    CategoryDAO categoryDAO;
    TagDAO tagDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO, CategoryDAO categoryDAO, TagDAO tagDAO) {
        this.articleDAO = articleDAO;
        this.categoryDAO = categoryDAO;
        this.tagDAO = tagDAO;
    }

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 查询title
     * @Date 1:19 PM 5/11/2020
     * @Param [title, queryPage]
     **/
    @Override
    public Page<Article> listArticleByTitle(String title, QueryPage queryPage) {
        List<Article> articleList = articleDAO.findByTitle(title, queryPage);
        return new Page<>(articleList, queryPage, articleList.size());

    }

    /**
     * @param categoryName
     * @param queryPage
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 根据分类查询文章
     * @Date 1:07 PM 5/11/2020
     * @Param [category, queryPage]
     */
    @Override
    public Page<Article> listArticleByCategory(String categoryName, QueryPage queryPage) {
        Category category = categoryDAO.findByName(categoryName);
        return getArticlePage(queryPage, category.getArticle_id());
    }

    /**
     * @param tagName
     * @param queryPage
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 根据tag查询文章
     * @Date 3:20 PM 5/11/2020
     * @Param [tag, queryPage]
     */
    @Override
    public Page<Article> listArticleByTag(String tagName, QueryPage queryPage) {
        Tag tag = tagDAO.findByName(tagName);
        return getArticlePage(queryPage, tag.getArticle_id());
    }

    private Page<Article> getArticlePage(QueryPage queryPage, List<String> article_id) {
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < queryPage.getLimit(); i++) {
            String id = article_id.get((queryPage.getPage() - 1) * queryPage.getLimit() + i);
            articles.add(articleDAO.findById(id));
        }
        return new Page<>(articles, queryPage, article_id.size());
    }

    /**
     * @param queryPage
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 分页列出文章
     * @Date 11:25 AM 5/11/2020
     * @Param [queryPage]
     */
    @Override
    public Page<Article> listArticle(QueryPage queryPage) {
        List<Article> articleList = articleDAO.findAll(queryPage);
        long totalNumber = articleDAO.total();
        return new Page<>(articleList, queryPage, totalNumber);
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
    public void addArticle(Article article) {
        articleDAO.add(article);
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
    public void updateArticle(Article article) {
        articleDAO.update(article);
    }

    @Override
    public void deleteArticle(String id) {
        articleDAO.deleteById(id);
    }

    @Override
    public Article findArticle(String id) {
        return articleDAO.findById(id);
    }

    @Override
    public Page<Category> listCategory(QueryPage queryPage) {
        List<Category> categoryList = categoryDAO.findAll(queryPage);
        long total = categoryDAO.total();
        return new Page<>(categoryList, queryPage, total);
    }

    @Override
    public List<Category> listCategory() {
        return categoryDAO.findAll(null);
    }

    @Override
    public void updateCategory(String categoryName) {

    }

    @Override
    public void deleteCategory(String categoryName) {

    }

    @Override
    public Page<Tag> listTag(QueryPage queryPage) {
        List<Tag> tagList = tagDAO.findAll(queryPage);
        long total = tagDAO.total();
        return new Page<>(tagList, queryPage, total);
    }

    @Override
    public List<Tag> listTag() {
        return tagDAO.findAll(null);
    }

    @Override
    public void updateTag(String tagName) {

    }

    @Override
    public void deleteTag(String tagName) {

    }
}
