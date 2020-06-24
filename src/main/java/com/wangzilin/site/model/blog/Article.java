package com.wangzilin.site.model.blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Author: wangzilinn@gmail.com
 * @Description: 文章映射实体
 * @Date: Created in 3:51 PM 5/7/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Data
@NoArgsConstructor
public class Article implements Serializable {
    @Id
    private String id;

    @NotBlank
    private String title;
    private String cover;
    private String author;
    private String content;
    private String contentMd;
    private String categoryName;
    private ArrayList<String> tagNames;
    private String state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
