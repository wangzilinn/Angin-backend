package com.wangzilin.site.controller.article;

import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Tag;
import com.wangzilin.site.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:46 PM 5/15/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@RequestMapping("/api/article/tag")
public class ArticleTagController {

    @Autowired
    private ArticleService.TagService tagService;

    @PostMapping
    public Response add(@RequestBody Tag tag) {
        tagService.add(tag);
        return new Response<>();
    }

    @DeleteMapping
    public Response delete(@RequestParam String name) {
        tagService.delete(name);
        return new Response<>();
    }

    @PutMapping
    public Response update(@RequestParam String from, @RequestParam String to) {
        tagService.update(from, to);
        return new Response<>();
    }

    @GetMapping
    public Response<Tag> name(@RequestParam String name) {
        return new Response<>(tagService.find(name));
    }
}