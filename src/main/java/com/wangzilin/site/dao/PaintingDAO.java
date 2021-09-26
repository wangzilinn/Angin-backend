package com.wangzilin.site.dao;

import com.wangzilin.site.model.file.Image;
import com.wangzilin.site.model.file.Painting;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 12:02 PM 06/30/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Repository
@NoArgsConstructor
public class PaintingDAO {
    @Resource
    private MongoTemplate mongoTemplateForFile;
    private final String PAINTING_COLLECTION = "painting";

    public Painting findRandomPainting() {
        SampleOperation matchStage = Aggregation.sample(1);
        Aggregation aggregation = Aggregation.newAggregation(matchStage);
        AggregationResults<Painting> output = mongoTemplateForFile.aggregate(aggregation, PAINTING_COLLECTION,
                Painting.class);
        return output.getMappedResults().get(0);
    }

    public Painting findPaintingById(String id) {
        return mongoTemplateForFile.findOne(new Query(Criteria.where("id").is(id)), Painting.class, PAINTING_COLLECTION);
    }




}
