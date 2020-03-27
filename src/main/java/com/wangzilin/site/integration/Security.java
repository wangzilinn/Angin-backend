package com.***REMOVED***.site.integration;

import com.***REMOVED***.site.auth.AuthErrorHandler;
import com.***REMOVED***.site.auth.TokenAuthFilter;
import com.***REMOVED***.site.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//这样就可以在Controller上配置权限
public class Security extends WebSecurityConfigurerAdapter {

    /**
     * 权限不足错误信息处理:认证错误, 鉴权错误
     */
    final private AuthErrorHandler authErrorHandler;

    /**
     * 过滤器, jwt校验过滤器，从http头部Authorization字段读取token并校验
     */
    final private TokenAuthFilter tokenAuthFilter;
    /**
     * 用户读取用户数据
     */
    final private UserService userService;

    public Security(AuthErrorHandler authErrorHandler, TokenAuthFilter tokenAuthFilter, UserService userService) {
        this.authErrorHandler = authErrorHandler;
        this.tokenAuthFilter = tokenAuthFilter;
        this.userService = userService;
    }


    /**
     * 用户密码加密器
     * 用户的明文密码会使用该加密器进行加密, 之后与数据库进行比对
     *
     * @return 加密器
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取AuthenticationManager（认证管理器），可以在其他地方使用
     *
     * @return .
     * @throws Exception .
     */
    @Bean(name = "authenticationMangerBean")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 返回一个userService, 用于导入用户数据
     *
     * @param authenticationManagerBuilder .
     * @throws Exception .
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService);
    }


    /**
     * 配置过滤规则
     *
     * @param httpSecurity .
     * @throws Exception .
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //基于token, 不需要csrf???
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 设置myUnauthorizedHandler处理认证失败、鉴权失败
                .exceptionHandling().authenticationEntryPoint(authErrorHandler).accessDeniedHandler(authErrorHandler)
                .and()
                //下面开始设置权限
                .authorizeRequests()
                .antMatchers("/hello/**").authenticated()
                .antMatchers("/chat/**").authenticated()
                .antMatchers("/card/**").authenticated()
                .anyRequest().permitAll();

        httpSecurity.addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();

    }


    /**
     * 配置跨源访问
     *
     * @return .
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
