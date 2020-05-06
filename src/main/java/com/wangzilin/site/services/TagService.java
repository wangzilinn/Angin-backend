package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.blog.Tag;
import com.***REMOVED***.site.util.QueryPage;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 9:40 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface TagService extends IService<Tag> {
    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Tag>
     * @Author ***REMOVED***
     * @Description 查询所有tag, 每个Tag对象包含添加了这个Tag的文章的数量
     * @Date 9:42 PM 5/7/2020
     * @Param []
     **/
    List<Tag> findAll();

    IPage<Tag> list(Tag tag, QueryPage queryPage);

    void add(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Tag>
     * @Author ***REMOVED***
     * @Description 根据文章Id查询其对应的Tag
     * @Date 9:45 PM 5/7/2020
     * @Param [id]
     **/
    List<Tag> findByArticleId(Long id);

}
