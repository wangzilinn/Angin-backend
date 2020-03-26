package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.model.SignRequest;
import com.***REMOVED***.site.services.UserService;
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


@RestController
@RequestMapping(value = "/auth")
public class AuthController {


    final private static org.slf4j.Logger log = LoggerFactory.getLogger(AuthController.class);

    final private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<String> SignIn(@Valid @RequestBody SignRequest authRequest, BindingResult bindingResult) throws AuthenticationException {

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach((fieldError) -> log.info(fieldError.getField()));
            return new ResponseEntity<>("输入格式错误", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final String token = userService.signIn(authRequest.getUserId(), authRequest.getPassword());

        return new ResponseEntity<>(token, HttpStatus.OK);
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
        userService.addUser(signRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
