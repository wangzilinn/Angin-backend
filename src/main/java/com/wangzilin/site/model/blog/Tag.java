package com.wangzilin.site.model.blog;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:Tag实体
 * @Date: Created in 4:01 PM 5/7/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Data
@NoArgsConstructor
public class Tag implements Serializable {
    @Id
    private String id;

    @NotNull
    private String name;

    private List<String> articleId;

    public Tag(String name) {
        this.name = name;
        this.articleId = new ArrayList<>();
    }

}
