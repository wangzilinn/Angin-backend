package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.annotation.WebLog;
import com.***REMOVED***.site.model.DTO.Response;
import com.***REMOVED***.site.model.blog.Comment;
import com.***REMOVED***.site.services.CommentService;
import com.***REMOVED***.site.util.QueryPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/list")
    @WebLog
    public Response list(Comment comment, QueryPage queryPage) {

        return new Response<>(super.getData(commentService.list(comment, queryPage)));
    }
}

