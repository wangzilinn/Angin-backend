package com.wangzilin.site.services.impl;

import com.wangzilin.site.dao.ArticleDAO;
import com.wangzilin.site.dao.CategoryDAO;
import com.wangzilin.site.dao.TagDAO;
import com.wangzilin.site.model.DTO.QueryPage;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Article;
import com.wangzilin.site.model.blog.Category;
import com.wangzilin.site.model.blog.Tag;
import com.wangzilin.site.services.ArticleService;
import com.wangzilin.site.services.CommentService;
import com.wangzilin.site.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 10:56 PM 5/6/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    final private ArticleDAO articleDAO;
    final private CommentService commentService;
    final private FileService fileService;

    public ArticleServiceImpl(ArticleDAO articleDAO, CommentService commentService, FileService fileService) {
        this.articleDAO = articleDAO;
        this.commentService = commentService;
        this.fileService = fileService;
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
        //article collection更新
        String id = articleDAO.add(article).getId();
        log.info("new article id = " + id);
    }

    @Override
    public void deleteArticle(String id) {
        articleDAO.deleteById(id);
        //删除评论
        commentService.deleteByArticleId(id);
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

    @Override
    public Response.Page<Article.Abstract> listArticleAbstractByCategoryAndTag(String category, String tag,
                                                                               QueryPage queryPage) {
        long numberOfArticles = articleDAO.countByCategoryNameAndTagName(category, tag);
        List<Article> articleList = articleDAO.findByCategoryNameAndTagName(category, tag, queryPage);
        return new Response.Page<>(Article.convertToAbstract(articleList), queryPage, numberOfArticles);
    }

    @Override
    public Response.Page<Article.Abstract> listArticle(int page, int limit, String title, String category, String tag) {
        QueryPage queryPage = new QueryPage(page, limit);
        Response.Page<Article.Abstract> abstractPage;
        if (title != null) {
            abstractPage = listArticleAbstractByTitle(title, queryPage);
        } else if (tag != null && category != null) {
            abstractPage = listArticleAbstractByCategoryAndTag(category, tag, queryPage);
        } else if (category != null) {
            abstractPage = listArticleAbstractByCategory(category, queryPage);
        } else if (tag != null) {
            abstractPage = listArticleAbstractByTag(tag, queryPage);
        } else {
            abstractPage = listArticleAbstract(queryPage);
        }
        // 为没有封面的摘要加上封面

        abstractPage.getElements().forEach(anAbstract -> {
            if (anAbstract.getIsPaintingCover()) {
                try {
                    anAbstract.setCover(fileService.getRandomPaintingId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return abstractPage;
    }


    /**
     * 分页列出文章
     *
     * @param queryPage -
     * @return -
     */
    @Override
    public Response.Page<Article.Abstract> listArticleAbstract(QueryPage queryPage) {
        List<Article> articleList = articleDAO.findAll(queryPage);
        long totalNumber = articleDAO.total();
        return new Response.Page<>(Article.convertToAbstract(articleList), queryPage, totalNumber);
    }

    /**
     * @return java.util.List<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
     * @Description 查询title
     * @Date 1:19 PM 5/11/2020
     * @Param [title, queryPage]
     **/
    @Override
    public Response.Page<Article.Abstract> listArticleAbstractByTitle(String title, QueryPage queryPage) {
        List<Article> articleList = articleDAO.findByTitle(title, queryPage);
        return new Response.Page<>(Article.convertToAbstract(articleList), queryPage, articleList.size());
    }


    @Override
    public Response.Page<Article.Abstract> listArticleAbstractByCategory(String categoryName, QueryPage queryPage) {
        long numberOfArticle = articleDAO.countByCategoryName(categoryName);
        List<Article> articleList = articleDAO.findByCategoryName(categoryName, queryPage);
        return new Response.Page<>(Article.convertToAbstract(articleList), queryPage, numberOfArticle);
    }


    @Override
    public Response.Page<Article.Abstract> listArticleAbstractByTag(String tagName, QueryPage queryPage) {
        long numberOfArticle = articleDAO.countByTagName(tagName);
        List<Article> articleList = articleDAO.findByTagName(tagName, queryPage);
        return new Response.Page<>(Article.convertToAbstract(articleList), queryPage, numberOfArticle);
    }


    @Service
    static class CategoryServiceImpl implements ArticleService.CategoryService {

        private final CategoryDAO categoryDAO;

        private Long totalCategories;

        @Autowired
        public CategoryServiceImpl(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
            this.totalCategories = categoryDAO.total();
        }

        @Override
        public void add(Category category) {
            categoryDAO.add(category);
            totalCategories++;
        }

        @Override
        public void delete(String name) {
            categoryDAO.deleteByName(name);
        }

        @Override
        public void update(String from, String to) {
            categoryDAO.updateName(from, to);
        }

        @Override
        public Response.Page<Category> list(QueryPage queryPage) {
            List<Category> categoryList = categoryDAO.findAll(queryPage);
            return new Response.Page<>(categoryList, queryPage, totalCategories);
        }

        @Override
        public List<Category> list() {
            return categoryDAO.findAll(null);
        }

        @Override
        public Category find(String name) {
            return categoryDAO.findByName(name);
        }
    }

    @Service
    static class TagServiceImpl implements ArticleService.TagService {

        private final TagDAO tagDAO;
        private Long totalTags;

        @Autowired
        public TagServiceImpl(TagDAO tagDAO) {
            this.tagDAO = tagDAO;
            this.totalTags = tagDAO.total();
        }

        @Override
        public void add(Tag tag) {
            tagDAO.add(tag);
            totalTags++;
        }

        @Override
        public void delete(String name) {
            tagDAO.deleteByName(name);
        }

        @Override
        public void update(String from, String to) {
            tagDAO.updateName(from, to);

        }

        @Override
        public Response.Page<Tag> list(QueryPage queryPage, String categoryName) {
            if (categoryName == null)
                return new Response.Page<>(tagDAO.findAll(queryPage), queryPage, totalTags);
            else {
                long numberOfTag = tagDAO.countByCategoryName(categoryName);
                return new Response.Page<>(tagDAO.findByCategoryName(categoryName, queryPage), queryPage, numberOfTag);
            }
        }

        @Override
        public List<Tag> list(String categoryName) {
            if (categoryName == null) {
                return tagDAO.findAll();
            } else {
                return tagDAO.findByCategoryName(categoryName);
            }
        }

        @Override
        public List<Tag> find(String name) {
            return tagDAO.findByName(name);
        }
    }

}
