package com.wangzilin.site.services.impl;

import com.wangzilin.site.dao.ImageDAO;
import com.wangzilin.site.dao.PaintingDAO;
import com.wangzilin.site.manager.CacheManager;
import com.wangzilin.site.model.file.Image;
import com.wangzilin.site.model.file.Painting;
import com.wangzilin.site.services.FileService;
import com.wangzilin.site.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 9:22 PM 6/9/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    final private ImageDAO imageDAO;
    final private PaintingDAO paintingDAO;
    final private CacheManager cacheManager;

    public FileServiceImpl(ImageDAO imageDAO, PaintingDAO paintingDAO, CacheManager cacheManager) {
        this.imageDAO = imageDAO;
        this.paintingDAO = paintingDAO;
        this.cacheManager = cacheManager;
    }

    /**
     * @param image 传入的图片实体
     * @return 图片的Id
     */
    @Override
    public String addImage(Image image) {
        return imageDAO.addImage(image).getId();
    }

    /**
     * @param file 传入的文件实体
     * @return 图片的id
     */
    @Override
    public String addImage(MultipartFile file) {
        try {
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setCreatedTime(new Date());
            image.setContent(new Binary(file.getBytes()));
            image.setContentType(file.getContentType());
            image.setSize(file.getSize());
            return addImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Image findImage(String id) {
        return imageDAO.findImageById(id);
    }

    @Override
    public void deleteImage(String id) {
        imageDAO.deleteImageById(id);
    }


    @Override
    public List<String> getRandomPaintingId(int num) {
        List<Painting> randomPaintings = paintingDAO.findRandomPainting(num);
        randomPaintings.forEach(painting -> {
            cacheManager.put(CacheManager.CacheType.PAINTING, painting.getId(), painting);
        });
        return randomPaintings.stream().map(Painting::getId).collect(Collectors.toList());
    }

    @Override
    public byte[] getPaintingContentById(String id, boolean zoom) throws IOException {
        Painting painting = cacheManager.get(CacheManager.CacheType.PAINTING, id);
        if (painting == null) {
            painting = paintingDAO.findPaintingById(id);
        }
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(painting.getContent().getData()));
        if (zoom) {
            image = ImageUtil.zoomOutImage(image, 300);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public Painting getPaintingById(String id) {
        Painting painting = cacheManager.get(CacheManager.CacheType.PAINTING, id);
        if (painting == null) {
            painting = paintingDAO.findPaintingById(id);
        }
        return painting;
    }


}
