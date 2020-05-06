package com.***REMOVED***.site.model.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:Tag实体
 * @Date: Created in 4:01 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@TableName(value = "tb_tag")
public class Tag implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @TableField(exist = false)
    private Long count;

    public Tag() {

    }

    public Tag(String name) {
        this.name = name;
    }
}
