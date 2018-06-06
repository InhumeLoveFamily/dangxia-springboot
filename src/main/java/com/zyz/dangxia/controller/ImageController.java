package com.zyz.dangxia.controller;

import com.zyz.dangxia.base.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    public static final String ROOT = "upload-dir";

    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/{userId}")
    @ResponseBody
    public int upload(@PathVariable("userId") int userId,
                      @RequestParam("file") MultipartFile file) {
        logger.info("开始上传文件");
        if (file == null || file.isEmpty()) {
            return -1;
        }
        try {
            logger.info("begin uploaded " + file.getOriginalFilename() + "!");
            FileLoader.load(file);
//            Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
            logger.info("You successfully uploaded " + file.getOriginalFilename() + "!");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {

        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
