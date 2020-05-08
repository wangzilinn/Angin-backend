package com.***REMOVED***.site.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.***REMOVED***.site.model.blog.Article;
import com.***REMOVED***.site.model.blog.ArticleTag;
import com.***REMOVED***.site.model.blog.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 9:53 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    //根据文章ID查询其关联的标签信息
    List<Tag> findByArticleId(Long article);

    //根据标签名称查询关联的文章数据
    List<Article> findByTagName(String name);
}
