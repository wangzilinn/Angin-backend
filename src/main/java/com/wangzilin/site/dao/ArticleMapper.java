package com.***REMOVED***.site.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.***REMOVED***.site.model.blog.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 3:49 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<String> findArchivesDates();

    List<Article> findArchivesByDate(String date);
}
