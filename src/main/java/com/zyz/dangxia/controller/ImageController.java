package com.zyz.dangxia.controller;

import com.zyz.dangxia.base.FileLoader;
import com.zyz.dangxia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping()
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    public static final String ROOT = "static/";

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserService userService;

    @PostMapping("/icon/{userId}")
    @ResponseBody
    public int upload(@PathVariable("userId") int userId,
                      @RequestParam("file") MultipartFile file,
                      HttpServletRequest request) {
        logger.info("开始上传文件");
        if (file == null || file.isEmpty()) {
            return -1;
        }

        String filePath = request.getSession().getServletContext().getRealPath(ROOT);
        logger.info("filePath = {}", filePath);

        try {
            logger.info("begin uploaded " + file.getOriginalFilename() + "!");
            int result = userService.uploadIcon(file, filePath, userId);
            if (result == 1) {
                logger.info("You successfully uploaded " + file.getOriginalFilename() + "!");
            }
//            Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
        } catch (RuntimeException | IOException e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 1;
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<?> getFile(@PathVariable String filename) {
//
//        try {
//            return new ResponseEntity<>()
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/icon/{userId}")
    @ResponseBody
    public ResponseEntity<?> getIcon(@PathVariable("userId") int userId) {
        return userService.loadIcon(userId);
    }

}
