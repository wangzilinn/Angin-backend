package com.***REMOVED***.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.***REMOVED***.site.constants.BlogConstant;
import com.***REMOVED***.site.dao.ArticleMapper;
import com.***REMOVED***.site.model.blog.*;
import com.***REMOVED***.site.services.*;
import com.***REMOVED***.site.util.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 10:56 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Service
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
     * @Description 获得最新的8篇文章
     * @Date 4:27 PM 5/7/2020
     * @Param []
     **/
    @Override
    public List<Article> findAll() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getId);
        queryWrapper.eq(Article::getState, BlogConstant.DEFAULT_RELEASE_STATUS);
        IPage<Article> page = new Page<>(0, 8);
        return articleMapper.selectPage(page, queryWrapper).getRecords();
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
        IPage<Article> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getId);
        // 按照标题模糊查询
        queryWrapper.like(StringUtils.isNotBlank(article.getTitle()), Article::getTitle, article.getTitle());
        // 按照作者模糊查询
        queryWrapper.like(StringUtils.isNotBlank(article.getAuthor()), Article::getAuthor, article.getAuthor());
        // 按照分类精确查询
        queryWrapper.eq(StringUtils.isNotBlank(article.getCategory()), Article::getCategory, article.getCategory());
        IPage<Article> selectPage = articleMapper.selectPage(page, queryWrapper);
        selectPage.getRecords().forEach(this::initArticle);
        return selectPage;
    }

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 为article更新tag和categories信息
     * @Date 9:36 PM 5/8/2020
     * @Param [records]
     **/
    private void initArticle(Article article) {
        // TODO:补充分类数据:categoruService是不更新article这个表的,因此需要在这里重新更新下
        List<Category> categoryList = categoryService.findByArticleId(article.getId());
        if (categoryList.size() > 0) {
            article.setCategory(categoryList.get(0).getName());
        }
        List<Tag> tagList = tagService.findByArticleId(article.getId());
        article.setTags(tagList);
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
        Article article = articleMapper.selectById(id);
        initArticle(article);
        return article;
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
        if (article.getPublishTime() == null && article.getState().equals(BlogConstant.DEFAULT_RELEASE_STATUS)) {
            article.setPublishTime(new Date());
        }
        articleMapper.updateById(article);
        updateArticleCategoryTags(article);
    }

    @Override
    public void delete(Long id) {
        if (id != null && id != 0) {
            articleMapper.deleteById(id);
            //删除文章-分类表的关联
            articleCategoryService.deleteByArticleId(id);
            //删除文章-标签表的关联
            articleTagService.deleteByArticleId(id);
        }
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
        IPage<Article> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getId);
        queryWrapper.eq(Article::getState, BlogConstant.DEFAULT_RELEASE_STATUS);
        return articleMapper.selectPage(page, queryWrapper);
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
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getCategory, category);
        return articleMapper.selectList(queryWrapper);
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
        List<ArchivesWithArticle> archivesWithArticleList = new ArrayList<ArchivesWithArticle>();
        try {
            List<String> dates = articleMapper.findArchivesDates();
            dates.forEach(date -> {
                List<Article> sysArticleList = articleMapper.findArchivesByDate(date);
                ArchivesWithArticle archivesWithArticle = new ArchivesWithArticle(date, sysArticleList);
                archivesWithArticleList.add(archivesWithArticle);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return archivesWithArticleList;
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
        try {
            if (article.getState() == null) {
                article.setState(BlogConstant.DEFAULT_DRAFT_STATUS);
            }
            if (article.getPublishTime() == null && article.getState().equals(BlogConstant.DEFAULT_RELEASE_STATUS)) {
                article.setPublishTime(new Date());
            }
            if (article.getAuthor() == null) {
                article.setAuthor("佚名");
            }
            article.setEditTime(new Date());
            article.setCreateTime(new Date());
            articleMapper.insert(article);
            updateArticleCategoryTags(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 文章, 分类, 标签三者的关联
     * @Date 10:00 PM 5/8/2020
     * @Param [article]
     **/
    private void updateArticleCategoryTags(Article article) {
        if (article.getId() != 0) {
            if (article.getCategory() != null) {
                articleCategoryService.deleteByArticleId(article.getId());
                Category sysCategory = categoryService.getById(article.getCategory());
                if (sysCategory != null) {
                    articleCategoryService.add(new ArticleCategory(article.getId(), sysCategory.getId()));
                }
            }
            if (article.getTags() != null && article.getTags().size() > 0) {
                articleTagService.deleteByArticleId(article.getId());
                article.getTags().forEach(tag -> {
                    articleTagService.add(new ArticleTag(article.getId(), tag.getId()));
                });
            }
        }
    }
}
