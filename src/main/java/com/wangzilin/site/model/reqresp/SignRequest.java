package com.***REMOVED***.site.model.reqresp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignRequest {

    @NotNull(message = "账号必须填")
    @Pattern(regexp = "[a-zA-Z]{1}[a-zA-Z0-9_]{1,15}", message = "由字母数字下划线组成且开头必须是字母，不能超过16位")
    private String userId;

    @NotNull(message = "密码必须填")
    @Size(min = 6, max = 16, message = "密码6~16位")
    private String password;

    private boolean rememberMe;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
