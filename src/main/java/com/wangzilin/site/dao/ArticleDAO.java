package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.blog.Article;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:57 PM 5/10/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Repository
@NoArgsConstructor
public class ArticleDAO {
    @Resource
    private MongoTemplate mongoTemplateForArticle;

    public List<Article> findAll(int page, int limit) {
        mongoTemplateForArticle
    }
}
