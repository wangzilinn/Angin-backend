package com.***REMOVED***.site.model.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description: 这是储存所有分类标签的一个表
 * @Date: Created in 4:53 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@TableName("tb_category")
@NoArgsConstructor
public class Category implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull
    private String name;

    public Category(String name) {
        this.name = name;
    }

}
