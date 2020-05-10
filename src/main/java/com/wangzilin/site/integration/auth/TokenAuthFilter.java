package com.***REMOVED***.site.integration.auth;

import com.***REMOVED***.site.services.impl.UserServiceImpl;
import com.***REMOVED***.site.util.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
JWT过滤器每次请求应该只执行一次，所以继承OncePerRequestFilter，JWT过滤器的主要行为：

对于每次请求，从http头部Authorization字段中读取jwt
尝试解密jwt，如果正常解出，说明是合法用户
如果是合法用户，设置认证信息，认证通过.
*/

@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TokenAuthFilter.class);


    final private UserServiceImpl userServiceImpl;

    final private String tokenHeader = "Authorization";

    public TokenAuthFilter(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 从http头部读取jwt
        String authToken = request.getHeader(this.tokenHeader);
        if (authToken != null) {
            UserForAuth userForAuth = null;
            // 从jwt中解出账号与角色信息
            try {
                //这里没有验证Token是否过期
                String username = JwtUtil.getUsernameFromToken(authToken);
                userForAuth = userServiceImpl.loadUserByUsername(username);
            } catch (Exception e) {
                log.debug("异常详情", e);
                log.info("无效token");
            }

            // 如果jwt正确解出账号信息，说明是合法用户，设置认证信息，认证通过
            if (userForAuth != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userForAuth.getUsername(), null, userForAuth.getAuthorities());

                // 把请求的信息设置到UsernamePasswordAuthenticationToken details对象里面，包括发请求的ip等
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 设置认证信息
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 调用下一个过滤器
        filterChain.doFilter(request, response);
    }
}
