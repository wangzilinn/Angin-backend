package com.***REMOVED***.site.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.***REMOVED***.site.dao.LinkMapper;
import com.***REMOVED***.site.model.blog.Link;
import com.***REMOVED***.site.services.LinkService;
import com.***REMOVED***.site.util.QueryPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 12:16 AM 5/9/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Autowired
    private LinkMapper linkMapper;

    @Override
    public IPage<Link> list(Link link, QueryPage queryPage) {
        IPage<Link> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(link.getName()), Link::getName, link.getName());
        queryWrapper.orderByDesc(Link::getId);
        return linkMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void add(Link link) {
        linkMapper.insert(link);
    }

    @Override
    @Transactional
    public void update(Link link) {
        this.updateById(link);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        linkMapper.deleteById(id);
    }
}
