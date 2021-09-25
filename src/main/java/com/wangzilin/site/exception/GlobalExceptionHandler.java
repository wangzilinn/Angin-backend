package com.wangzilin.site.exception;

import com.wangzilin.site.model.DTO.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:14 PM 6/20/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    // 如果产生了异常，仍返回200响应码，错误信息在返回体中体现
    @ResponseStatus(HttpStatus.OK)
    public Response<?> userException(UserException userException) {
        log.error(userException.getMessage());
        log.error(Arrays.toString(userException.getStackTrace()));
        return new Response<>(userException.getCode(), userException.getMessage());
    }

    // 入参检查异常：
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response<?> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error(methodArgumentNotValidException.getMessage());
        return new Response<>(403, methodArgumentNotValidException.getMessage());
    }

}
