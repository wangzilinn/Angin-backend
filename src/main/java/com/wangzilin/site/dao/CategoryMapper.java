package com.***REMOVED***.site.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.***REMOVED***.site.model.blog.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 4:56 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> findCategoryByArticleId(Long id);
}
