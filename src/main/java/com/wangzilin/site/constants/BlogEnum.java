package com.wangzilin.site.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 10:08 PM 5/6/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Getter
@AllArgsConstructor
public enum BlogEnum {
    LOGIN_ERROR(500, "用户名或密码错误"),
    LOGIN_AUTHORIZATION(302, "登录状态失效，请重新登录"),
    PARAM_ERROR(401, "参数错误"),
    USER_ERROR(500, "获取用户信息失败"),
    LOGOUT_ERROR(500, "退出失败"),
    SYSTEM_ERROR(500, "系统内部错误"),
    COMMON_ERROR(500, "系统内部错误"),
    FILE_ERROR(400, "上传的文件为空"),
    COMMON_SUCCESS(200, "成功"),
    RESET_SUCCESS(200, "重置密码成功");

    private final int code;
    private final String msg;
}
