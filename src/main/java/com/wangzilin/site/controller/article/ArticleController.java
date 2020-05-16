package com.wangzilin.site.controller.article;

import com.wangzilin.site.model.DTO.Page;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Article;
import com.wangzilin.site.services.ArticleService;
import com.wangzilin.site.util.QueryPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    final private static org.slf4j.Logger log = LoggerFactory.getLogger(ArticleController.class);


    /**
     * @return com.wangzilin.site.model.DTO.Response
     * @Author wangzilin
     * @Description 添加新文章
     * @Date 2:46 PM 5/11/2020
     * @Param [article]
     **/

    @PostMapping
    public Response add(@RequestBody Article article) {
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
    public Response delete(@RequestParam(value = "id") String id) {
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
    public Response update(@RequestBody Article article) {
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
    public Response<Article> id(@RequestParam(value = "id") String id) {
        return new Response<>(articleService.findArticle(id));
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response<java.util.List < com.wangzilin.site.model.blog.Article>>
     * @Author wangzilin
     * @Description 分页列出文章, 可以带查询条件
     * @Date 2:48 PM 5/11/2020
     * @Param [page, limit]
     **/
    //TODO:list返回的不需要有文章内容,最好再多一个total字段
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


}
