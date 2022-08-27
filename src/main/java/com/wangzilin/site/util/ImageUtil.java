package com.wangzilin.site.util;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * 图片放大缩小
 */
public class ImageUtil {

    /**
     * 对图片进行放大
     *
     * @param originalImage 原始图片
     * @param times         放大倍数
     * @return
     */

    public static BufferedImage zoomInImage(BufferedImage originalImage, Integer times) {

        int width = originalImage.getWidth() * times;

        int height = originalImage.getHeight() * times;

        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());

        Graphics g = newImage.getGraphics();

        g.drawImage(originalImage, 0, 0, width, height, null);

        g.dispose();

        return newImage;

    }


    /**
     * 等比缩小图片
     *
     * @param originalImage 原始图片
     * @param width         等比缩小后的图片宽度
     * @return 新图片
     */
    public static BufferedImage zoomOutImage(BufferedImage originalImage, Integer width) {

        float ratio = (float) originalImage.getHeight() / originalImage.getHeight();

        int height = (int) (width * ratio);

        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());

        Graphics g = newImage.getGraphics();

        g.drawImage(originalImage, 0, 0, width, height, null);

        g.dispose();

        return newImage;

    }
}
