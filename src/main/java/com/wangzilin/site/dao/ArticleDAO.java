package com.wangzilin.site.dao;

import com.wangzilin.site.model.DTO.QueryPage;
import com.wangzilin.site.model.blog.Article;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

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

    public Article add(Article article) {
        return mongoTemplateForBlog.insert(article, ARTICLE_COLLECTION);
    }

    public Article deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplateForBlog.findAndRemove(query, Article.class, ARTICLE_COLLECTION);
    }

    public boolean updateCategory(String id, String newCategoryName) {
        return mongoTemplateForBlog.updateFirst(
                new Query(Criteria.where("_id").is(id)),
                new Update().set("categoryName", newCategoryName),
                ARTICLE_COLLECTION
        ).wasAcknowledged();
    }

    public boolean updateTag(String id, List<String> addedTags, List<String> deletedTags) {
        Update update = new Update();
        if (addedTags != null) {
            addedTags.forEach(addedTag -> {
                update.addToSet("tagNames", addedTag);
            });
        }
        if (deletedTags != null) {
            deletedTags.forEach(deletedTag -> {
                update.pull("tagNames", deletedTag);
            });
        }
        return mongoTemplateForBlog.updateFirst(
                new Query(Criteria.where("_id").is(id)),
                update,
                ARTICLE_COLLECTION
        ).wasAcknowledged();
    }

    public void update(Article article) {
        //若已存在相同id则替换
        mongoTemplateForBlog.save(article);
    }

    public long total() {
        return mongoTemplateForBlog.count(new Query(), Article.class, ARTICLE_COLLECTION);
    }

    public List<Article> findAll(QueryPage queryPage) {
        //这里减一是因为请求时第一个页面是1而mongodb内部第一个页面是0
        final Pageable pageableRequest = PageRequest.of(queryPage.getPageForMongoDB(), queryPage.getLimit());
        //创建查询对
        Query query = new Query();
        query.with(pageableRequest);
        query.with(Sort.by(Sort.Direction.DESC, "editTime"));
        return mongoTemplateForBlog.find(query, Article.class, ARTICLE_COLLECTION);
    }

    public List<Article> findByTitle(String title, QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPageForMongoDB(), queryPage.getLimit());
        //创建查询对象
        title = String.format("^.*%s.*$", title);
        Query query = new Query(Criteria.where("title").regex(title));
        query.with(pageableRequest);
        return mongoTemplateForBlog.find(query, Article.class, ARTICLE_COLLECTION);
    }

    public List<Article> findByCategoryName(String categoryName, QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPageForMongoDB(), queryPage.getLimit());
        return mongoTemplateForBlog.find(new Query(Criteria.where("categoryName").is(categoryName)).with(pageableRequest), Article.class,
                ARTICLE_COLLECTION);
    }

    public long countByCategoryName(String categoryName) {
        return mongoTemplateForBlog.count(new Query(Criteria.where("categoryName").is(categoryName)), Article.class,
                ARTICLE_COLLECTION);
    }


    public List<Article> findByTagName(String tagName, QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPageForMongoDB(), queryPage.getLimit());
        return mongoTemplateForBlog.find(new Query(Criteria.where("tagNames").is(tagName)).with(pageableRequest),
                Article.class, ARTICLE_COLLECTION);
    }

    public long countByTagName(String tagName) {
        return mongoTemplateForBlog.count(new Query(Criteria.where("tagNames").is(tagName)), Article.class,
                ARTICLE_COLLECTION);
    }

    public List<Article> findByCategoryNameAndTagName(String categoryName, String tagName, QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPageForMongoDB(), queryPage.getLimit());
        return mongoTemplateForBlog.find(new Query(Criteria.where("tagNames").is(tagName)
                .and("categoryName").is(categoryName)).with(pageableRequest), Article.class, ARTICLE_COLLECTION);
    }

    public long countByCategoryNameAndTagName(String categoryName, String tagName, QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPageForMongoDB(), queryPage.getLimit());
        return mongoTemplateForBlog.count(new Query(Criteria.where("tagNames").is(tagName)
                .and("categoryName").is(categoryName)).with(pageableRequest), Article.class, ARTICLE_COLLECTION);
    }


    public Article findById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplateForBlog.findOne(query, Article.class, ARTICLE_COLLECTION);
    }

}
