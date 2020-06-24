package com.wangzilin.site.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SimpleUserInfoRequest {

    @NotBlank(message = "账号必须填")
    //@Pattern(regexp = "[a-zA-Z]{1}[a-zA-Z0-9_]{1,15}", message = "由字母数字下划线组成且开头必须是字母，不能超过16位")
    private String username;

    @NotBlank(message = "密码必须填")
    @Size(min = 6, max = 16, message = "密码6~16位")
    private String password;

}
