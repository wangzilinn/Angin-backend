package com.wangzilin.site.controller;

import com.wangzilin.site.annotation.WebLog;
import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.model.file.Image;
import com.wangzilin.site.model.file.Painting;
import com.wangzilin.site.services.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 8:59 PM 6/9/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@RequestMapping("/api/img")
@Tag(name = "Image", description = "图片上传/下载接口")
@Slf4j
public class ImageController {

    final private FileService fileService;

    public ImageController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    @WebLog
    public Response<?> uploadImage(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty())
            return new Response<>("No image found");
        String imageId = fileService.addImage(file);
        return new Response<>(imageId);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable String id) {
        Image image = fileService.findImage(id);
        if (image == null) {
            return null;
        }
        return image.getContent().getData();
    }


    @GetMapping(value = "/painting/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[]  getPaintingContentById(@PathVariable String id,
                                          @RequestParam(value = "zoom", defaultValue = "true") boolean zoom) throws IOException {
        return fileService.getPaintingContentById(id, zoom);
    }

    @GetMapping(value = "/painting/detail/{id}")
    public Response<Painting> getPaintingDetailById(@PathVariable String id) {
        return new Response<>(fileService.getPaintingById(id));
    }


}
