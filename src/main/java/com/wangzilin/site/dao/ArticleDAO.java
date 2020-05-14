package com.wangzilin.site.dao;

import com.wangzilin.site.model.blog.Article;
import com.wangzilin.site.util.QueryPage;
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
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:57 PM 5/10/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Repository
@NoArgsConstructor
public class ArticleDAO {
    @Resource
    private MongoTemplate mongoTemplateForBlog;
    private final String ARTICLE_COLLECTION = "article";


    public List<Article> findAll(QueryPage queryPage) {
        //这里减一是因为请求时第一个页面是1而mongodb内部第一个页面是0
        final Pageable pageableRequest = PageRequest.of(queryPage.getPage() - 1, queryPage.getLimit());
        //创建查询对
        Query query = new Query();
        query.with(pageableRequest);
        return mongoTemplateForBlog.find(query, Article.class, ARTICLE_COLLECTION);
    }

    public long total() {
        Query query = new Query();
        return mongoTemplateForBlog.count(query, Article.class, ARTICLE_COLLECTION);
    }

    public List<Article> findByTitle(String title, QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPage(), queryPage.getLimit());
        //创建查询对象
        title = String.format("^.*%s.*$", title);
        Query query = new Query(Criteria.where("title").regex(title));
        query.with(pageableRequest);
        return mongoTemplateForBlog.find(query, Article.class, ARTICLE_COLLECTION);
    }


    public Article findById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplateForBlog.findOne(query, Article.class, ARTICLE_COLLECTION);
    }


    public void add(Article article) {
        mongoTemplateForBlog.save(article, ARTICLE_COLLECTION);
    }

    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplateForBlog.remove(query, ARTICLE_COLLECTION);
    }

    public void update(String id, Map<String, Object> updateMap) {
        Update update = new Update();
        for (Map.Entry<String, Object> item : updateMap.entrySet()) {
            update.set(item.getKey(), item.getValue());
        }
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplateForBlog.updateFirst(query, update, ARTICLE_COLLECTION);
    }

    public void update(Article article) {
        deleteById(article.getId());
        add(article);
    }


}
