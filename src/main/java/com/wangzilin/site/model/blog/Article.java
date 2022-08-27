package com.wangzilin.site.model.blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static List<Abstract> convertToAbstract(List<Article> articleList) {
        ArrayList<Article.Abstract> abstractList = new ArrayList<>();
        articleList.forEach(article -> abstractList.add(new Article.Abstract(article)));
        return abstractList;
    }

    /**
     * @Author: wangzilinn@gmail.com
     * @Description:
     * @Date: Created in 11:03 PM 06/27/2020
     * @Modified By:wangzilinn@gmail.com
     */
    @Data
    @NoArgsConstructor
    public static class Abstract implements Serializable {
        @Id
        private String id;

        @NotBlank
        private String title;
        private String cover;
        private String author;
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
        private Boolean isPaintingCover;


        public Abstract(Article article) {
            this.id = article.getId();
            this.title = article.getTitle();
            this.cover = article.getCover();
            this.isPaintingCover = StringUtils.isEmpty(this.cover);
            this.author = article.getAuthor();
            this.categoryName = article.getCategoryName();
            this.tagNames = article.getTagNames();
            this.state = article.getState();
            this.publishTime = article.getPublishTime();
            this.editTime = article.getEditTime();
            this.createTime = article.getCreateTime();
        }
    }
}
