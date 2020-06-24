package com.wangzilin.site.dao;

import com.wangzilin.site.model.DTO.QueryPage;
import com.wangzilin.site.model.blog.Category;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * @Date: Created in 12:55 PM 5/11/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Repository
@NoArgsConstructor
public class CategoryDAO {
    @Resource
    private MongoTemplate mongoTemplateForBlog;
    private final String CATEGORY_COLLECTION = "category";
    final private static org.slf4j.Logger log = LoggerFactory.getLogger(CategoryDAO.class);

    /**
     * @return com.wangzilin.site.model.blog.Category 返回保存的文件
     * @Author wangzilin
     * @Description
     * @Date 12:05 AM 5/16/2020
     * @Param [category]
     **/
    public Category add(Category category) {
        return mongoTemplateForBlog.save(category, CATEGORY_COLLECTION);
    }

    public boolean addArticle(String categoryName, String articleId) {
        return mongoTemplateForBlog.updateFirst(
                new Query(Criteria.where("name").is(categoryName)),
                new Update().addToSet("articleId", articleId),
                CATEGORY_COLLECTION
        ).wasAcknowledged();
    }

    public Category deleteByName(String name) {
        return mongoTemplateForBlog.findAndRemove(new Query(Criteria.where("name").is(name)), Category.class,
                CATEGORY_COLLECTION);
    }

    public boolean deleteArticle(String categoryName, String articleId) {
        return mongoTemplateForBlog.updateFirst(
                new Query(Criteria.where("name").is(categoryName)),
                new Update().pull("articleId", articleId),
                CATEGORY_COLLECTION
        ).wasAcknowledged();
    }

    public boolean updateName(String from, String to) {
        return mongoTemplateForBlog.updateFirst(
                new Query(Criteria.where("name").is(from)),
                new Update().set("name", to),
                CATEGORY_COLLECTION).wasAcknowledged();
    }

    public List<Category> findAll(QueryPage queryPage) {
        //创建查询对象
        if (queryPage != null) {
            Query query = new Query();
            //这里减一是因为请求时第一个页面是1而mongodb内部第一个页面是0
            final Pageable pageableRequest = PageRequest.of(queryPage.getPage() - 1, queryPage.getLimit());
            query.with(pageableRequest);
            List<Category> result = mongoTemplateForBlog.find(query, Category.class, CATEGORY_COLLECTION);
            log.info(result.toString());
            return result;
        }
        List<Category> result = mongoTemplateForBlog.findAll(Category.class, CATEGORY_COLLECTION);
        log.info(result.toString());
        return result;
    }

    public long total() {
        Query query = new Query();
        return mongoTemplateForBlog.count(query, Category.class, CATEGORY_COLLECTION);
    }

    public Category findById(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplateForBlog.findOne(query, Category.class, CATEGORY_COLLECTION);
    }

    public Category findByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplateForBlog.findOne(query, Category.class, CATEGORY_COLLECTION);
    }
}
