package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.annotation.WebLog;
import com.***REMOVED***.site.model.DTO.Page;
import com.***REMOVED***.site.model.DTO.Response;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.services.ArticleService;
import com.***REMOVED***.site.util.QueryPage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 10:54 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@RestController
@RequestMapping("/api/article")
@io.swagger.v3.oas.annotations.tags.Tag(name = "ArticleController", description = "文章管理接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    final private static org.slf4j.Logger log = LoggerFactory.getLogger(ArticleController.class);

    /**
     * @return com.***REMOVED***.site.model.DTO.Response<com.***REMOVED***.site.model.blog.Article>
     * @Author ***REMOVED***
     * @Description 根据id返回文章
     * @Date 2:46 PM 5/11/2020
     * @Param [id]
     **/
    @WebLog
    @GetMapping
    public Response<Article> id(@RequestParam(value = "id") String id) {
        return new Response<>(articleService.findArticle(id));
    }

    /**
     * @return com.***REMOVED***.site.model.DTO.Response
     * @Author ***REMOVED***
     * @Description 添加新文章
     * @Date 2:46 PM 5/11/2020
     * @Param [article]
     **/
    @WebLog
    @PostMapping
    public Response add(@RequestBody Article article) {
        //添加文档上传时间
        article.setCreateTime(new Date());
        article.setEditTime(new Date());
        articleService.addArticle(article);
        return new Response<>();
    }

    /**
     * @return com.***REMOVED***.site.model.DTO.Response
     * @Author ***REMOVED***
     * @Description 根据id删除文章
     * @Date 2:47 PM 5/11/2020
     * @Param [id]
     **/
    @WebLog
    @DeleteMapping
    public Response delete(@RequestParam(value = "id") String id) {
        articleService.deleteArticle(id);
        return new Response<>();
    }

    /**
     * @return com.***REMOVED***.site.model.DTO.Response
     * @Author ***REMOVED***
     * @Description 更新文章
     * @Date 2:47 PM 5/11/2020
     * @Param [article]
     **/
    @WebLog
    @PutMapping
    public Response update(@RequestBody Article article) {
        articleService.updateArticle(article);
        return new Response<>();
    }


    /**
     * @return com.***REMOVED***.site.model.DTO.Response<java.util.List < com.***REMOVED***.site.model.blog.Article>>
     * @Author ***REMOVED***
     * @Description 分页列出文章, 可以带查询条件
     * @Date 2:48 PM 5/11/2020
     * @Param [page, limit]
     **/
    //TODO:list返回的不需要有文章内容,最好再多一个total字段
    @WebLog
    @GetMapping("/list")
    public Response<Page<Article>> list(@RequestParam(value = "page") int page,
                                        @RequestParam(value = "limit") int limit,
                                        @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "tag", required = false) String tag) {
        QueryPage queryPage = new QueryPage(page, limit);
        if (title != null) {
            return new Response<>(articleService.listArticleByTitle(title, queryPage));
        }
        if (category != null) {
            return new Response<>(articleService.listArticleByCategory(category, queryPage));
        }
        if (tag != null) {
            return new Response<>(articleService.listArticleByTag(tag, queryPage));
        }
        return new Response<>(articleService.listArticle(queryPage));
    }


    /**
     * @return com.***REMOVED***.site.model.DTO.Response<java.util.List < com.***REMOVED***.site.model.blog.Category>>
     * @Author ***REMOVED***
     * @Description 列出所有分类, 有参数则分页, 无参数则不分页
     * @Date 2:48 PM 5/11/2020
     * @Param [page, limit]
     **/
    @WebLog
    @GetMapping("/category")
    public Response category(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "limit", required = false) Integer limit) {
        if (page == null) {
            return new Response<>(articleService.listCategory());
        }
        return new Response<>(articleService.listCategory(new QueryPage(page, limit)));
    }


    /**
     * @return com.***REMOVED***.site.model.DTO.Response<java.util.List < com.***REMOVED***.site.model.blog.Tag>>
     * @Author ***REMOVED***
     * @Description 分页列出所有tag, , 有参数则分页, 无参数则不分页
     * @Date 2:48 PM 5/11/2020
     * @Param [page, limit]
     **/
    @WebLog
    @GetMapping("/tag")
    public Response tag(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "limit", required = false) Integer limit) {
        if (page == null) {
            return new Response<>(articleService.listTag());
        }
        return new Response<>(articleService.listTag(new QueryPage(page, limit)));
    }
}
