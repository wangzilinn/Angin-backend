package com.wangzilin.site.controller;

import com.wangzilin.site.model.DTO.Response;
import com.wangzilin.site.services.FileService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 8:59 PM 6/9/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@RequestMapping("/api/img")
public class ImageController {

    @Autowired
    private FileService fileService;
    final private static org.slf4j.Logger log = LoggerFactory.getLogger(ImageController.class);

    @PostMapping
    public Response uploadImage(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty())
            return new Response<>("No image found");

        fileService.addImage(file);
        return new Response<>();
    }
}
