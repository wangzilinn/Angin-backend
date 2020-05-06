package com.***REMOVED***.site.model.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description: 用于封装Article表按时间归档
 * @Date: Created in 4:44 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivesWithArticle implements Serializable {
    private String date;
    private List<Article> articles;
}
