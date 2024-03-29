package com.wangzilin.site.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangzilin.site.annotation.WebLog;
import com.wangzilin.site.exception.UserException;
import com.wangzilin.site.manager.CacheManager;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.DTO.SimpleUserInfoRequest;
import com.wangzilin.site.model.user.User;
import com.wangzilin.site.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping(value = "/api/user")
@Tag(name = "UserController", description = "鉴权接口")
@Slf4j
@Validated
public class UserController {


    final private UserService userService;
    final private CacheManager cacheManager;

    public UserController(UserService userService, CacheManager cacheManager) {
        this.userService = userService;
        this.cacheManager = cacheManager;
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response
     * @Author wangzilin
     * @Description 用户登录
     * @Date 11:39 PM 5/10/2020
     * @Param [username, password]
     **/
    @PostMapping("/signIn")
    @WebLog
    public Response<?> SignIn(@Valid @NotNull @RequestBody SimpleUserInfoRequest simpleUserInfoRequest) throws AuthenticationException {

        final User user = userService.auth(simpleUserInfoRequest.getUsername(), simpleUserInfoRequest.getPassword());
        if (user == null) {
            throw new UserException(422, "找不到用户");
        }
        return new Response<>(user);
    }

    @PostMapping("/info")
    @WebLog
    public Response<?> getInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return new Response<>(user);
    }

    /**
     * @return com.wangzilin.site.model.DTO.Response
     * @Author wangzilin
     * @Description 用户注册
     * @Date 11:47 PM 5/10/2020
     * @Param [username, password]
     **/
    @PostMapping("/signUp")
    public Response<?> SignUp(@NotBlank @RequestParam(value = "captcha") String captcha,
                              @NotBlank @RequestParam(value = "token") String token,
                              @Valid @NotNull @RequestBody SimpleUserInfoRequest simpleUserInfoRequest) throws AuthenticationException {
        String captchaText = (String) cacheManager.get(CacheManager.CacheType.CAPTCHA, token);
        if (captchaText == null) {
            throw new UserException(400, "session中无验证码");
        }
        if (captcha.equals(captchaText)) {
            cacheManager.remove(CacheManager.CacheType.CAPTCHA, captcha);
            userService.signUp(simpleUserInfoRequest);
        } else {
            throw new UserException(400, "验证码错误");
        }
        return new Response<>();
    }

    @GetMapping("/github/{username}")
    @WebLog
    public Response<?> github(@PathVariable String username) throws JsonProcessingException {
        return new Response<>(userService.getGithubInfo(username));
    }
}
