package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.auth.AuthService;
import com.***REMOVED***.site.auth.Result;
import com.***REMOVED***.site.model.LoginRequest;
import com.***REMOVED***.site.util.MiscUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * login
     *
     * @param authRequest
     * @param bindingResult
     * @return ResponseEntity<Result>
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Result> login(@Valid @RequestBody LoginRequest authRequest, BindingResult bindingResult) throws AuthenticationException {

        if (bindingResult.hasErrors()) {
            Result res = MiscUtil.getValidateError(bindingResult);
            return new ResponseEntity<Result>(res, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final String token = authService.login(authRequest.getAccount(), authRequest.getPassword());

        // Return the token
        Result res = new Result(200, "ok");
        res.putData("token", token);
        return ResponseEntity.ok(res);
    }
}
