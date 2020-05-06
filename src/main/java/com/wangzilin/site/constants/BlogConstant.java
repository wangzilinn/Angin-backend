package com.***REMOVED***.site.constants;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 9:52 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public interface BlogConstant {
    /**
     * 前后端分离，前端携带的Token标识
     */
    String AUTHORIZATION = "Authorization";

    String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    /**
     * 成功标记
     */
    int SUCCESS = 200;

    /**
     * 错误标记
     */
    int ERROR = 500;

    /**
     * 文章已发布状态标识：1
     */
    String DEFAULT_RELEASE_STATUS = "1";

    /**
     * 文章未发布状态标识：1
     */
    String DEFAULT_DRAFT_STATUS = "0";

    /**
     * User_Agent
     */
    String USER_AGENT = "User-Agent";

    /**
     * 博客前台默认每页显示多少条博文
     */
    int DEFAULT_PAGE_LIMIT = 12;

    /**
     * Articles页评论分类
     */
    int COMMENT_SORT_ARTICLE = 0;

    /**
     * 默认每页显示多少条评论数据
     */
    int COMMENT_PAGE_LIMIT = 8;

    /**
     * Links页评论分类
     */
    int COMMENT_SORT_LINKS = 1;

    /**
     * About页评论分类
     */
    int COMMENT_SORT_ABOUT = 2;
}
