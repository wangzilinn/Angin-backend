package com.wangzilin.site.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:57 AM 06/30/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Data
@NoArgsConstructor
public class Painting {
    @Id
    private String id;

    private String title;
    private String artist;
    private String detail_url;
    private String label;
    private Date createdTime; // 上传时间
    @NotNull
    private Binary content; // 文件内容
    private String contentType; // 文件类型
}
