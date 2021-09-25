package com.wangzilin.site.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 10:48 PM 5/12/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Component
public class CorsFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest require = (HttpServletRequest) req;
        // 允许前端跨域携带cookie
        response.addHeader("Access-Control-Allow-Credentials", "true");
        // 如果有上面这一句,则下面不能写成:
        // response.setHeader("Access-Control-Allow-Origin", "*");
        // 必须写成这样：
        response.addHeader("Access-Control-Allow-Origin", require.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token,content-type");

        // //前端ajax请求OPTIONS直接放行，解决不能得到header里token问题
        // if (require.getMethod().equals("OPTIONS")) {
        //     response.getWriter().println("ok");
        //     return;
        // }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
