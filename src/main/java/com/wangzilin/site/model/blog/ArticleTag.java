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
 * @Description: 一个article对应多个tag, 因此article_id列有重复
 * @Date: Created in 9:48 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@TableName("tb_article_tag")
public class ArticleTag implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull
    @TableField("article_id")
    private Long articleId;

    @NotNull
    @TableField("tag_id")
    private Long tagId;

    public ArticleTag(Long articleId, Long tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
