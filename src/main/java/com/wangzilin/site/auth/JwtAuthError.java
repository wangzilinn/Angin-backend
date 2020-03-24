package com.***REMOVED***.site.auth;

import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*当认证失败，系统会抛出认证失败异常，可以配置我们自己的认证失败处理类，同样鉴权失败也可以配置我们自己的失败处理类。
JwtAuthError继承AuthenticationEntryPoint（认证失败接口）、AccessDeniedHandler（鉴权失败接口），
重写了这2个接口类的失败处理方法，其实JwtAuthError可以分为2个类，我们合二为一了。*/
@Service
public class JwtAuthError implements AuthenticationEntryPoint, AccessDeniedHandler {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JwtAuthError.class);

    // 认证失败处理，返回401 json数据
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("认证失败处理，返回401 json数据");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"status\":401,\"message\":\"Unauthorized or invalid token\"}");
    }

    // 鉴权失败处理，返回403 json数据
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("鉴权失败处理，返回403 json数据");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"status\":403,\"message\":\"Forbidden\"}");
    }
}
