package com.***REMOVED***.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.***REMOVED***.site.mapper.CategoryMapper;
import com.***REMOVED***.site.model.blog.Category;
import com.***REMOVED***.site.services.ArticleCategoryService;
import com.***REMOVED***.site.services.CategoryService;
import com.***REMOVED***.site.util.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 5:05 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    //    这个干嘛用的???
    @Override
    public IPage<Category> list(Category category, QueryPage queryPage) {
        IPage<Category> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(category.getName()), Category::getName, category.getName());
        queryWrapper.orderByDesc(Category::getId);
        return categoryMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void add(Category category) {
        if (!exists(category)) {
            categoryMapper.insert(category);
        }
    }

    @Override
    @Transactional
    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    @Override
    public void delete(Long id) {
        categoryMapper.deleteById(id);
//      删除与该文类相关文章关联的信息
        articleCategoryService.deleteByCategoryId(id);
    }

    @Override
    public List<Category> findByArticleId(Long id) {
//        TODO:可优化:通过XML实现的一下这个方法:
        return categoryMapper.findCategoryByArticleId(id);
    }

    private boolean exists(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName, category.getName());
        return categoryMapper.selectList(queryWrapper).size() > 0;
    }
}
