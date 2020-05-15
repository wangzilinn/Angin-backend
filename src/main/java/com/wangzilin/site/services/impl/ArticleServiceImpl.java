package com.wangzilin.site.services.impl;

import com.wangzilin.site.dao.ArticleDAO;
import com.wangzilin.site.dao.CategoryDAO;
import com.wangzilin.site.dao.TagDAO;
import com.wangzilin.site.model.DTO.Page;
import com.wangzilin.site.model.blog.Article;
import com.wangzilin.site.model.blog.Category;
import com.wangzilin.site.model.blog.Tag;
import com.wangzilin.site.services.ArticleService;
import com.wangzilin.site.util.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 10:56 PM 5/6/2020
 * @Modified By:wangzilinn@gmail.com
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
     * @param article
     * @return void
     * @Author wangzilin
     * @Description 添加
     * @Date 4:47 PM 5/7/2020
     * @Param [article]
     */
    @Override
    public void addArticle(Article article) {
        articleDAO.add(article);
    }

    @Override
    public void deleteArticle(String id) {
        articleDAO.deleteById(id);
    }

    /**
     * @param article
     * @return void
     * @Author wangzilin
     * @Description 删改
     * @Date 4:36 PM 5/7/2020
     * @Param [article]
     */
    @Override
    public void updateArticle(Article article) {
        articleDAO.update(article);
    }

    @Override
    public Article findArticle(String id) {
        return articleDAO.findById(id);
    }

    /**
     * @param queryPage
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
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
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
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
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
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
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
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

    @Service
    static class CategoryServiceImpl implements ArticleService.CategoryService {

        @Autowired
        CategoryDAO categoryDAO;

        @Override
        public void add(Category category) {
            categoryDAO.
        }

        @Override
        public void delete(String name) {

        }

        @Override
        public void update(String from, String to) {

        }

        @Override
        public Page<Category> list(QueryPage queryPage) {
            return null;
        }

        @Override
        public List<Category> list() {
            return null;
        }

        @Override
        public Category find(String name) {
            return null;
        }
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
    public void addCategory(Category category) {

    }

    @Override
    public void updateCategory(String from, String to) {

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
    public void addTag(Tag tag) {

    }

    @Override
    public void updateTag(String from, String to) {

    }

    @Override
    public void deleteTag(String tagName) {

    }
}
