package com.***REMOVED***.site.auth;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
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

@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    private String tokenHeader = "Authorization";

    private String tokenPrefix = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 从http头部读取jwt
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenPrefix)) {

            final String authToken = authHeader.substring(tokenPrefix.length() + 1); // The part after "Bearer "
            String username = null, role = null;

            // 从jwt中解出账号与角色信息
            try {
                username = jwtUtil.getUsernameFromToken(authToken);
                role = jwtUtil.getClaimFromToken(authToken, "role", String.class);
            } catch (Exception e) {
                log.debug("异常详情", e);
                log.info("无效token");
            }

            // 如果jwt正确解出账号信息，说明是合法用户，设置认证信息，认证通过
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, AuthUser.getAuthoritiesByRole(role));

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
