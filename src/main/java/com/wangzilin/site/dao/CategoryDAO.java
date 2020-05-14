package com.wangzilin.site.dao;

import com.wangzilin.site.model.blog.Category;
import com.wangzilin.site.util.QueryPage;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public List<Category> findAll(QueryPage queryPage) {
        log.info("findAll");
        //创建查询对象
        if (queryPage != null) {
            Query query = new Query();
            final Pageable pageableRequest = PageRequest.of(queryPage.getPage(), queryPage.getLimit());
            query.with(pageableRequest);
            return mongoTemplateForBlog.find(query, Category.class, CATEGORY_COLLECTION);
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
