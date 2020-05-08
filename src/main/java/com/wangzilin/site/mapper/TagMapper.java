package com.***REMOVED***.site.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.***REMOVED***.site.model.blog.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 4:10 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findByArticleId(long id);
}
