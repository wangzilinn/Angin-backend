package com.wangzilin.site.controller.article;

import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Category;
import com.wangzilin.site.services.ArticleService;
import com.wangzilin.site.util.QueryPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 4:15 PM 5/15/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@RequestMapping("/api/article/category")
@Tag(name = "Article's category", description = "文章分类接口")
public class ArticleCategoryController {

    @Autowired
    private ArticleService.CategoryService categoryService;

    final private static org.slf4j.Logger log = LoggerFactory.getLogger(ArticleCategoryController.class);

    @PostMapping
    public Response addCategory(@RequestBody Category category) {
        categoryService.add(category);
        return new Response<>();
    }


    @DeleteMapping
    public Response deleteCategory(@RequestParam String name) {
        categoryService.delete(name);
        return new Response<>();
    }

    @PutMapping
    public Response editCategory(@RequestParam String from, @RequestParam String to) {
        categoryService.update(from, to);
        return new Response<>();
    }

    @GetMapping
    public Response<Category> name(@RequestParam String name) {
        return new Response<>(categoryService.find(name));
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response<java.util.List < com.wangzilin.site.model.blog.Category>>
     * @Author wangzilin
     * @Description 列出所有分类, 有参数则分页, 无参数则不分页
     * @Date 2:48 PM 5/11/2020
     * @Param [page, limit]
     **/

    @GetMapping("/list")
    public Response list(@RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "limit", required = false) Integer limit) {
        if (page == null) {
            return new Response<>(categoryService.list());
        }
        return new Response<>(categoryService.list(new QueryPage(page, limit)));
    }
}
