package com.wangzilin.site.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:22 PM 6/20/2020
 * @Modified By:wangzilinn@gmail.com
 */
public class PowerStripException extends RuntimeException {
    @Getter
    @Setter
    private Integer code;

    public PowerStripException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
