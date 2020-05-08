package com.***REMOVED***.site.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.***REMOVED***.site.model.blog.Link;
import com.***REMOVED***.site.util.QueryPage;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 12:00 AM 5/9/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface LinkService extends IService<Link> {
    IPage<Link> list(Link link, QueryPage queryPage);

    void add(Link link);

    void update(Link link);

    void delete(Long id);
}
