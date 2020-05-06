package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.blog.Category;
import com.***REMOVED***.site.util.QueryPage;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 4:51 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface CategoryService extends IService<Category> {
    IPage<Category> list(Category category, QueryPage queryPage);

    void add(Category category);

    void update(Category category);

    void delete(Long id);

    List<Category> findByArticleId(Long id);

}
