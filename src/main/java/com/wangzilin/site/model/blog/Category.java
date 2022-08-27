package com.wangzilin.site.model.blog;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: wangzilinn@gmail.com
 * @Description: 这是储存所有分类标签的一个表
 * @Date: Created in 4:53 PM 5/7/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Data
@NoArgsConstructor
public class Category implements Serializable {
    @Id
    private String id;

    @NotBlank
    private String name;

    public Category(String name) {
        this.name = name;
    }

}
