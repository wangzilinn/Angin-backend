package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.annotation.WebLog;
import com.***REMOVED***.site.model.DTO.Response;
import com.***REMOVED***.site.services.CommentService;
import com.***REMOVED***.site.util.QueryPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:15 PM 5/8/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@RestController
@RequestMapping("/api/comment")
@Tag(name = "CommentController", description = "评论管理接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    @WebLog
    public Response list(@RequestParam(value = "page") int page,
                         @RequestParam(value = "limit") int limit,
                         @RequestParam(value = "id", required = false) Long id,
                         @RequestParam(value = "about", required = false) Boolean showAbout) {
        QueryPage queryPage = new QueryPage(page, limit);
        if (id != null) {
            return new Response<>(commentService.listByArticleId(id));
        }
        if (showAbout != null && showAbout) {
            return new Response<>(commentService.listAbout(queryPage));
        }
        return new Response<>(commentService.list(queryPage));
    }
}

