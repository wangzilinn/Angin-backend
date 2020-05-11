package com.***REMOVED***.site.services.impl;

import com.***REMOVED***.site.dao.ArticleDAO;
import com.***REMOVED***.site.dao.CategoryDAO;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.model.blog.Category;
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

    public ArticleServiceImpl(ArticleDAO articleDAO, CategoryDAO categoryDAO) {
        this.articleDAO = articleDAO;
        this.categoryDAO = categoryDAO;
    }

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 查询title
     * @Date 1:19 PM 5/11/2020
     * @Param [title, queryPage]
     **/
    @Override
    public List<Article> listByTitle(String title, QueryPage queryPage) {
        return articleDAO.findByTitle(title, queryPage);

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
    public List<Article> listByCategory(String categoryName, QueryPage queryPage) {
        Category category = categoryDAO.findByName(categoryName);
        List<Long> article_id = category.getArticle_id();
        ArrayList<Article> articles = new ArrayList<>();
        for (Long id : article_id) {
            articles.add(articleDAO.findById(id));
        }
        return articles;
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
    public List<Article> list(QueryPage queryPage) {
        return articleDAO.findAll(queryPage);
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
    public void update(Article article) {
        articleDAO.update(article);
    }

    @Override
    public void delete(Long id) {
        articleDAO.deleteById(id);
    }

}
