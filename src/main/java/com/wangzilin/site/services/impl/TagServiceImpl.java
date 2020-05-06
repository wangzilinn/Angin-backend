package com.***REMOVED***.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.***REMOVED***.site.mapper.TagMapper;
import com.***REMOVED***.site.model.blog.Tag;
import com.***REMOVED***.site.services.ArticleTagService;
import com.***REMOVED***.site.services.TagService;
import com.***REMOVED***.site.util.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 9:45 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * @return java.util.List<com.***REMOVED***.site.model.blog.Tag>
     * @Author ***REMOVED***
     * @Description 查询所有tag, 每个Tag对象包含添加了这个Tag的文章的数量
     * @Date 9:42 PM 5/7/2020
     * @Param []
     **/
    @Override
    public List<Tag> findAll() {
        return tagMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public IPage<Tag> list(Tag tag, QueryPage queryPage) {
        IPage<Tag> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(tag.getName()), Tag::getName, tag.getName());
        queryWrapper.orderByDesc(Tag::getId);
        return tagMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void add(Tag tag) {
        if (!exist(tag)) {
            tagMapper.insert(tag);
        }
    }

    private boolean exist(Tag tag) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName, tag.getName());
        return tagMapper.selectList(queryWrapper).size() > 0;
    }

    @Override
    public void update(Tag tag) {
        tagMapper.updateById(tag);
    }

    @Override
    public void delete(Long id) {
        tagMapper.deleteById(id);
//        删除该标签与文章有关联的信息
        articleTagService.deleteByTagsId(id);
    }

    /**
     * @param id
     * @return java.util.List<com.***REMOVED***.site.model.blog.Tag>
     * @Author ***REMOVED***
     * @Description 根据文章Id查询其对应的Tag
     * @Date 9:45 PM 5/7/2020
     * @Param [id]
     */
    @Override
    public List<Tag> findByArticleId(Long id) {
        return null;
    }
}
