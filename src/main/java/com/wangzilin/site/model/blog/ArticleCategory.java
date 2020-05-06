package com.***REMOVED***.site.model.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description: 这里存一个文章id对应的分类id
 * @Date: Created in 5:09 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@TableName("tb_article_category")
@NoArgsConstructor
public class ArticleCategory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull
    @TableField("article_id")
    private Long articleId;

    @NotNull
    @TableField("category_id")
    private Long categoryId;

    public ArticleCategory(Long articleId, Long categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }
}
