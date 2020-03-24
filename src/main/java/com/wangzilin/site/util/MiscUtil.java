package com.***REMOVED***.site.util;

import com.***REMOVED***.site.auth.Result;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class MiscUtil {

    @SuppressWarnings("unused")
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MiscUtil.class);

    static public Result getValidateError(BindingResult bindingResult) {

        if (!bindingResult.hasErrors())
            return null;
        Map<String, String> fieldErrors = new HashMap<String, String>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getCode() + "|" + error.getDefaultMessage());
        }

        Result ret = new Result(422, "输入错误"); //rfc4918 - 11.2. 422: Unprocessable Entity
        ret.putData("fieldErrors", fieldErrors);

        return ret;
    }

}