package com.***REMOVED***.site.services.impl;

import com.***REMOVED***.site.dao.ArticleDAO;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.services.ArticleService;
import com.***REMOVED***.site.util.QueryPage;
import org.springframework.stereotype.Service;

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

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    /**
     * @param article
     * @param queryPage
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 查询文章
     * @Date 11:22 AM 5/11/2020
     * @Param [article, page, limit]
     */
    @Override
    public List<Article> list(Article article, QueryPage queryPage) {
        if (article.getTitle() != null) {
            return articleDAO.findByTitle(article.getTitle(), queryPage);
        } else if (article.getCategory() != null) {
            //先从categories表中获得文章的id list
            //之后根据id返回列表
        }
        return null;
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
