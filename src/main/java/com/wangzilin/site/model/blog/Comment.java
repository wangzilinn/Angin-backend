package com.wangzilin.site.model.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: 2020-05-11 15:40 by Wang Zilin
 * @Modified By:wangzilinn@gmail.com
 */
@Data
@NoArgsConstructor
@TableName(value = "comment")
public class Comment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("article_title")
    private String articleTitle; //在评论总览页面方便看

    @TableField("article_id")
    private Long articleId;

    @TableField("reply_id")
    private Long replyId;

    @NotNull
    private String username;

    private String avatar;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    private String content;
}
