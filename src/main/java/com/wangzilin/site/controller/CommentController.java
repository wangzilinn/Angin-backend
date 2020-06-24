package com.wangzilin.site.controller;

import com.wangzilin.site.annotation.WebLog;
import com.wangzilin.site.model.DTO.QueryPage;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.blog.Comment;
import com.wangzilin.site.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:15 PM 5/8/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@RequestMapping("/api/comment")
@Tag(name = "CommentController", description = "评论管理接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    @WebLog
    public Response<?> add(@RequestBody Comment comment) {
        if (comment.getDate() == null) {
            comment.setDate(new Date());
        }
        commentService.add(comment);
        return new Response<>();
    }

    @GetMapping("/list")
    @WebLog
    public Response<?> list(@RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "limit", defaultValue = "4") int limit,
                            @RequestParam(value = "id", required = false) String id,
                            @RequestParam(value = "about", required = false) Boolean showAbout) {
        QueryPage queryPage = new QueryPage(page, limit);
        if (id != null) {
            return new Response<>(commentService.listByArticleId(queryPage, id));
        }
        if (showAbout != null && showAbout) {
            return new Response<>(commentService.listAbout(queryPage));
        }
        return new Response<>(commentService.list(queryPage));
    }
}

