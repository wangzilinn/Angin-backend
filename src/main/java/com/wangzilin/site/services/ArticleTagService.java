package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.blog.ArticleTag;
import com.***REMOVED***.site.model.blog.Tag;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 10:01 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface ArticleTagService extends IService<ArticleTag> {
    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 新增关联关系
     * @Date 10:03 PM 5/7/2020
     * @Param [articleTag]
     **/
    void add(ArticleTag articleTag);

    List<Tag> findByArticleId(Long articleId);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 根据文章id删除添加于其上的标签
     * @Date 10:07 PM 5/7/2020
     * @Param [id]
     **/
    void deleteByArticleId(Long id);

    void deleteByTagsId(Long id);
}
