package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.model.DTO.Response;
import com.***REMOVED***.site.model.DTO.SignRequest;
import com.***REMOVED***.site.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/auth")
@Tag(name = "AuthController", description = "鉴权接口")
public class AuthController {


    final private static org.slf4j.Logger log = LoggerFactory.getLogger(AuthController.class);

    final private UserServiceImpl userServiceImpl;

    public AuthController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * 用户登录
     *
     * @param authRequest   .
     * @param bindingResult .
     * @return 返回token
     * @throws AuthenticationException .
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST, produces = "application/json")
    public Response SignIn(@Valid @RequestBody SignRequest authRequest, BindingResult bindingResult) throws AuthenticationException {

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach((fieldError) -> log.info(fieldError.getField()));
            return new Response<>(422, "输入格式错误");
        }

        final String token = userServiceImpl.signIn(authRequest.getUserId(), authRequest.getPassword());

        // 变为字典格式如:{"token":"e77e2268-7252-4b29-b606-acbca981836d"}} 输出
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);

        return new Response<>(map);
    }


    /**
     * 用户注册
     *
     * @param signRequest   .
     * @param bindingResult .
     * @return .
     * @throws AuthenticationException .
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> SignUp(@Valid @RequestBody SignRequest signRequest, BindingResult bindingResult) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach((fieldError) -> log.info(fieldError.getField()));
            return new ResponseEntity<>("输入格式错误", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userServiceImpl.addUser(signRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
