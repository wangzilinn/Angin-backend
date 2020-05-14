package com.wangzilin.site.dao;

import com.wangzilin.site.model.blog.Tag;
import com.wangzilin.site.util.QueryPage;
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
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 3:08 PM 5/11/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Repository
@NoArgsConstructor
public class TagDAO {
    @Resource
    private MongoTemplate mongoTemplateForBlog;
    private final String TAG_COLLECTION = "tag";

    public List<Tag> findAll(QueryPage queryPage) {
        if (queryPage != null) {
            Query query = new Query();
            final Pageable pageableRequest = PageRequest.of(queryPage.getPage(), queryPage.getLimit());
            query.with(pageableRequest);
            return mongoTemplateForBlog.find(query, Tag.class, TAG_COLLECTION);
        }
        return mongoTemplateForBlog.findAll(Tag.class, TAG_COLLECTION);
    }

    public long total() {
        Query query = new Query();
        return mongoTemplateForBlog.count(query, Tag.class, TAG_COLLECTION);
    }

    public Tag findByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplateForBlog.findOne(query, Tag.class, TAG_COLLECTION);
    }
}
