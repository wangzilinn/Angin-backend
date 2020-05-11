package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.util.QueryPage;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    private String COLLECT_NAME = "article";

    public List<Article> findAll(QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPage(), queryPage.getLimit());
        //创建查询对象
        Query query = new Query();
        query.with(pageableRequest);
        return mongoTemplateForArticle.find(query, Article.class, COLLECT_NAME);
    }

    public List<Article> findByTitle(String title, QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPage(), queryPage.getLimit());
        //创建查询对象
        title = String.format("^.*%s.*$", title);
        Query query = new Query(Criteria.where("title").regex(title));
        query.with(pageableRequest);
        return mongoTemplateForArticle.find(query, Article.class, COLLECT_NAME);
    }

    public Article findById(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplateForArticle.findOne(query, Article.class, COLLECT_NAME);
    }

    public void add(Article article) {
        mongoTemplateForArticle.save(article, COLLECT_NAME);
    }

    public void deleteById(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplateForArticle.remove(query, COLLECT_NAME);
    }

    public void update(Long id, Map<String, Object> updateMap) {
        Update update = new Update();
        for (Map.Entry<String, Object> item : updateMap.entrySet()) {
            update.set(item.getKey(), item.getValue());
        }
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplateForArticle.updateFirst(query, update, COLLECT_NAME);
    }

    public void update(Article article) {
        deleteById(article.getId());
        add(article);
    }
}
