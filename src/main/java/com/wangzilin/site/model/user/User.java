package com.***REMOVED***.site.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 12:03 AM 5/9/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@TableName(value = "tb_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String salt;
    private String avatar;
    private String introduce;
    private String remark;
}
