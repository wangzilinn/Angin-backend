package com.***REMOVED***.site.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.***REMOVED***.site.model.blog.Category;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 4:56 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> findCategoryByArticleId(Long id);
}
