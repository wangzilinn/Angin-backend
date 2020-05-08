package com.***REMOVED***.site.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.***REMOVED***.site.annotation.WebLog;
import com.***REMOVED***.site.model.DTO.Response;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.services.ArticleService;
import com.***REMOVED***.site.util.QueryPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 10:54 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@RestController
@RequestMapping("/api/article")
@Tag(name = "ArticleController", description = "文章管理接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @WebLog
    @PostMapping("/list")
    public Response<Map<String, Object>> findByPage(@RequestBody Article sysArticle, QueryPage queryPage) {
        IPage<Article> page = articleService.list(sysArticle, queryPage);
        Map<String, Object> data = new HashMap<>();
        data.put("rows", page.getRecords());
        data.put("total", page.getTotal());
        return new Response<>(data);
    }
}
