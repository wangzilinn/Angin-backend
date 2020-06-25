package com.wangzilin.site.controller.article;

import com.wangzilin.site.model.DTO.QueryPage;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Category;
import com.wangzilin.site.services.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 4:15 PM 5/15/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@RequestMapping("/api/article/category")
@Tag(name = "Article's category", description = "文章分类接口")
@Slf4j
@Validated
public class ArticleCategoryController {

    final private ArticleService.CategoryService categoryService;

    public ArticleCategoryController(ArticleService.CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Response<?> addCategory(@Valid @NotNull @RequestBody Category category) {
        categoryService.add(category);
        return new Response<>();
    }


    @DeleteMapping
    public Response<?> deleteCategory(@NotBlank @RequestParam String name) {
        categoryService.delete(name);
        return new Response<>();
    }

    @PutMapping
    public Response<?> editCategory(@NotBlank @RequestParam String from, @NotBlank @RequestParam String to) {
        categoryService.update(from, to);
        return new Response<>();
    }

    @GetMapping
    public Response<Category> name(@NotBlank @RequestParam String name) {
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
    public Response<?> list(@Min(1) @RequestParam(value = "page", required = false) Integer page,
                            @Min(1) @RequestParam(value = "limit", required = false) Integer limit) {
        if (page == null) {
            return new Response<>(categoryService.list());
        }
        return new Response<>(categoryService.list(new QueryPage(page, limit)));
    }
}
