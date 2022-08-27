package com.wangzilin.site.services;

import com.wangzilin.site.model.file.Image;
import com.wangzilin.site.model.file.Painting;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    List<String> getRandomPaintingId(int num) throws IOException;

    byte[] getPaintingContentById(String id, boolean zoom) throws IOException;

    Painting getPaintingById(String id);
}
