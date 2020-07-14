package com.wangzilin.site.controller.article;

import com.wangzilin.site.annotation.WebLog;
import com.wangzilin.site.model.DTO.QueryPage;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Tag;
import com.wangzilin.site.services.ArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:46 PM 5/15/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@RequestMapping("/api/article/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Article's tag", description = "文章标签管理接口")
@Validated
public class ArticleTagController {

    final private ArticleService.TagService tagService;

    public ArticleTagController(ArticleService.TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @WebLog(description = "add")
    public Response<?> add(@Valid @RequestBody Tag tag) {
        tagService.add(tag);
        return new Response<>();
    }

    @DeleteMapping
    public Response<?> delete(@NotBlank @RequestParam String name) {
        tagService.delete(name);
        return new Response<>();
    }

    @PutMapping
    public Response<?> update(@NotBlank @RequestParam String from, @NotBlank @RequestParam String to) {
        tagService.update(from, to);
        return new Response<>();
    }

    @GetMapping
    public Response<?> name(@NotBlank @RequestParam String name) {
        return new Response<>(tagService.find(name));
    }

    @GetMapping("/list")
    public Response<?> list(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "limit", required = false) Integer limit,
                            @RequestParam(value = "categoryName", required = false) String categoryName) {
        if (page == null) {
            return new Response<>(tagService.list(categoryName));
        }
        return new Response<>(tagService.list(new QueryPage(page, limit), categoryName));
    }
}