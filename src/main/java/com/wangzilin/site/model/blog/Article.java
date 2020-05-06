package com.***REMOVED***.site.model.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description: 文章映射实体
 * @Date: Created in 3:51 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@TableName("tb_article")
public class Article implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotNull
    private String title;
    private String cover;
    private String author;
    private String content;
    //    数据库表名和字段名不同
    @TableField("content_md")
    private String contentMd;
    private String category;
    private String state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("publish_time")
    private Date publishTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("edit_time")
    private Date editTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private List<Tag> tags;

    public Article() {

    }

    public Article(String category) {
        this.category = category;
    }

    public Article(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    public Article(String title, String category) {
        this.title = title;
        this.category = category;
    }
}
