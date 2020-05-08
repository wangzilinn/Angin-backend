package com.***REMOVED***.site.model.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 12:00 AM 5/9/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@TableName("tb_link")
public class Link {
    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private String url;
}
