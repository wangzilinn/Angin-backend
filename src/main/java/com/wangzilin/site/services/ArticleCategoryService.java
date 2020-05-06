package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.blog.ArticleCategory;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 5:31 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface ArticleCategoryService extends IService<ArticleCategory> {
    void add(ArticleCategory articleCategory);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 根据文章Id删除
     * @Date 5:32 PM 5/7/2020
     * @Param [id]
     **/
    void deleteByArticleId(Long id);

    /**
     * @return void
     * @Author ***REMOVED***
     * @Description 根据分类id删除
     * @Date 5:33 PM 5/7/2020
     * @Param [id]
     **/
    void deleteByCategoryId(Long id);
}
