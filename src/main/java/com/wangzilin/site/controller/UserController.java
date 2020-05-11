package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.model.DTO.Response;
import com.***REMOVED***.site.model.user.User;
import com.***REMOVED***.site.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/user")
@Tag(name = "UserController", description = "鉴权接口")
public class UserController {


    final private static org.slf4j.Logger log = LoggerFactory.getLogger(UserController.class);

    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return com.***REMOVED***.site.model.DTO.Response
     * @Author ***REMOVED***
     * @Description 用户登录
     * @Date 11:39 PM 5/10/2020
     * @Param [username, password]
     **/
    @PostMapping("/signIn")
    public Response SignIn(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password) throws AuthenticationException {

        final User user = userService.auth(username, password);
        if (user == null) {
            return new Response<>(422, "找不到用户");
        }
        return new Response<>(user);
    }


    /**
     * @return com.***REMOVED***.site.model.DTO.Response
     * @Author ***REMOVED***
     * @Description 用户注册
     * @Date 11:47 PM 5/10/2020
     * @Param [username, password]
     **/
    @PostMapping("/signUp")
    public Response SignUp(@RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password) throws AuthenticationException {
        userService.add(new User(username, password));
        return new Response<>();
    }
}
