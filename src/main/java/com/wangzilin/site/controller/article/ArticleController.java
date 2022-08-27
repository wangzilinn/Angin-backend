package com.wangzilin.site.controller.article;

import com.wangzilin.site.annotation.WebLog;
import com.wangzilin.site.model.DTO.QueryPage;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Article;
import com.wangzilin.site.services.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 10:54 PM 5/6/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@RequestMapping("/api/article")
@Tag(name = "Article", description = "文章管理接口")
@Slf4j
@Validated
public class ArticleController {

    final private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response
     * @Author wangzilin
     * @Description 添加新文章
     * @Date 2:46 PM 5/11/2020
     * @Param [article]
     **/

    @PostMapping
    @RolesAllowed("admin")
    @WebLog
    public Response<?> add(@Valid @RequestBody Article article) {
        //添加文档上传时间
        article.setCreateTime(new Date());
        article.setEditTime(new Date());
        articleService.addArticle(article);
        return new Response<>();
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response
     * @Author wangzilin
     * @Description 根据id删除文章
     * @Date 2:47 PM 5/11/2020
     * @Param [id]
     **/

    @DeleteMapping
    @RolesAllowed({"admin"})
    @WebLog
    public Response<?> delete(@NotBlank @RequestParam(value = "id") String id) {
        articleService.deleteArticle(id);
        return new Response<>();
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response
     * @Author wangzilin
     * @Description 更新文章
     * @Date 2:47 PM 5/11/2020
     * @Param [article]
     **/

    @PutMapping
    @RolesAllowed({"admin"})
    @WebLog
    public Response<?> update(@Valid @RequestBody Article article) {
        //将上传到服务器的时间作为更新时间
        article.setEditTime(new Date());
        articleService.updateArticle(article);
        return new Response<>();
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response<com.wangzilin.site.model.blog.Article>
     * @Author wangzilin
     * @Description 根据id返回文章
     * @Date 2:46 PM 5/11/2020
     * @Param [id]
     **/

    @GetMapping
    @WebLog
    public Response<Article> id(@NotBlank @RequestParam(value = "id") String id) {
        return new Response<>(articleService.findArticle(id));
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response<java.util.List < com.wangzilin.site.model.blog.Article>>
     * @Author wangzilin
     * @Description 分页列出文章, 可以带查询条件
     * @Date 2:48 PM 5/11/2020
     * @Param [page, limit]
     **/
    @GetMapping("/list")
    public Response<Response.Page<Article.Abstract>> list(@RequestParam(value = "page") int page,
                                                          @RequestParam(value = "limit") int limit,
                                                          @RequestParam(value = "title", required = false) String title,
                                                          @RequestParam(value = "category", required = false) String category,
                                                          @RequestParam(value = "tag", required = false) String tag) throws IOException {

        return new Response<>(articleService.listArticle(page, limit, title, category, tag));
    }


}
