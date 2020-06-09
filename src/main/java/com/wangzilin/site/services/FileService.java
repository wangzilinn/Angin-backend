package com.wangzilin.site.services;

import com.wangzilin.site.model.file.Image;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 9:21 PM 6/9/2020
 * @Modified By:wangzilinn@gmail.com
 */
public interface FileService {

    String addImage(Image image);

    String addImage(MultipartFile file);

    Image findImage(String id);

    void deleteImage(String id);
}
