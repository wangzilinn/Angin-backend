package com.wangzilin.site.dao;

import com.wangzilin.site.model.file.Image;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 9:15 PM 6/9/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Repository
@NoArgsConstructor
public class ImageDAO {
    @Resource
    private MongoTemplate mongoTemplateForFile;
    private final String IMG_COLLECTION = "img";

    public Image addImage(Image image) {
        return mongoTemplateForFile.save(image, IMG_COLLECTION);
    }

    public Image deleteImageById(String id) {
        return mongoTemplateForFile.findAndRemove(new Query(Criteria.where("id").is(id)), Image.class, IMG_COLLECTION);
    }

    public Image findImageById(String id) {
        return mongoTemplateForFile.findOne(new Query(Criteria.where("id").is(id)), Image.class, IMG_COLLECTION);
    }

}
