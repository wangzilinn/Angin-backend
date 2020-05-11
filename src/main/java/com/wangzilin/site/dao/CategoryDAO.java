package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.blog.Category;
import com.***REMOVED***.site.util.QueryPage;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 12:55 PM 5/11/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Repository
@NoArgsConstructor
public class CategoryDAO {
    @Resource
    private MongoTemplate mongoTemplateForArticle;
    private final String CATEGORY_COLLECTION = "category";

    public List<Category> findAll(QueryPage queryPage) {
        final Pageable pageableRequest = PageRequest.of(queryPage.getPage(), queryPage.getLimit());
        //创建查询对象
        Query query = new Query();
        query.with(pageableRequest);
        return mongoTemplateForArticle.find(query, Category.class, CATEGORY_COLLECTION);
    }

    public Category findById(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplateForArticle.findOne(query, Category.class, CATEGORY_COLLECTION);
    }

    public Category findByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplateForArticle.findOne(query, Category.class, CATEGORY_COLLECTION);
    }
}
