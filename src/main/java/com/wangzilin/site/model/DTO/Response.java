package com.***REMOVED***.site.model.DTO;

import com.***REMOVED***.site.constants.BlogConstant;
import com.***REMOVED***.site.constants.BlogEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 9:56 PM 5/6/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Builder
@ToString
@AllArgsConstructor //Build解决父类依赖
public class Response<T> implements Serializable {
    @Getter
    @Setter
    private int code = BlogConstant.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;

    public Response() {
        super();
    }

    public Response(T data) {
        super();
        this.data = data;
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public Response(BlogEnum blogEnum) {
        super();
        this.code = blogEnum.getCode();
        this.msg = blogEnum.getMsg();
    }

    public Response(Throwable throwable) {
        super();
        this.code = BlogConstant.ERROR;
        this.msg = throwable.getMessage();
    }
}
