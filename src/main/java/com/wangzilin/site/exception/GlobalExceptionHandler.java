package com.wangzilin.site.exception;

import com.wangzilin.site.model.DTO.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(PowerStripException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<?> powerStripException(PowerStripException powerStripException) {
        log.error(powerStripException.getMessage());
        log.error(Arrays.toString(powerStripException.getStackTrace()));
        return new Response<>(powerStripException.getCode(), powerStripException.getMessage());
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response<?> userException(UserException userException) {
        log.error(userException.getMessage());
        log.error(Arrays.toString(userException.getStackTrace()));
        return new Response<>(userException.getCode(), userException.getMessage());
    }

}
