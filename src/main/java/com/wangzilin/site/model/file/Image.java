package com.wangzilin.site.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 9:07 PM 6/9/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Data
@NoArgsConstructor
public class Image {
    @Id
    private String id;

    private String name; // 文件名
    private Date createdTime; // 上传时间
    private Binary content; // 文件内容
    //TODO:不同文件大小的处理
    private String contentType; // 文件类型
    private long size;
}
