package com.wangzilin.site.controller;

import com.google.code.kaptcha.Producer;
import com.wangzilin.site.manager.CacheManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 1:49 PM 6/17/2020
 * @Modified By:wangzilinn@gmail.com
 */
@RestController
@Tag(name = "Captcha", description = "生成验证码")
@RequestMapping("/api/captcha")
public class CaptchaController {

    final private Producer captchaProducer;
    final private CacheManager cacheManager;

    public CaptchaController(Producer captchaProducer, CacheManager cacheManager) {
        this.captchaProducer = captchaProducer;
        this.cacheManager = cacheManager;
    }

    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE})
    @Operation(summary = "请求验证码")
    public byte[] getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0); //某些远古浏览器不支持Cache-Control
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        //生成验证码
        String capText = captchaProducer.createText();
        String capToken = captchaProducer.createText();
        cacheManager.put(CacheManager.CacheType.CAPTCHA, capToken, capText);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(capToken.getBytes(StandardCharsets.US_ASCII));
        //创造验证码图片：
        BufferedImage bi = captchaProducer.createImage(capText);
        ImageIO.write(bi, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
