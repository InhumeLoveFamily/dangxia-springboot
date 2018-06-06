package com.zyz.dangxia.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


/**
 * Created by thinkpad on 2017/9/21.
 */
public class FileLoader {

    @Autowired
    private static HttpServletRequest request;

    private static Logger logger = LoggerFactory.getLogger(FileLoader.class);

    public static String load(MultipartFile file/*,String filePath*/) {
        if (file.isEmpty()) {
            //       logger.info("上传文件为空");
            throw new RuntimeException("上传文件为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();

        //   logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //    logger.info("上传的后缀名为：" + suffixName);
        //String subPath = suffixName.substring(1);
        // 文件上传后的路径
//        String filePath = "c:/test/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        String path = (new File("src/main/webapp")).getAbsolutePath();
        logger.info("path = {}", path);
//        logger.info("contextpath = {}",request.getContextPath());
        File dir = new File(path + "/upload");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File dest = new File(path + "/upload/" + fileName);
        logger.info(dest.getAbsolutePath());
        if (suffixName.equals(".jpg") || suffixName.equals(".png") || suffixName.equals(".gif") || suffixName.equals(".jpeg") || suffixName.equals(".bmp")) {
            try {
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                file.transferTo(dest);
                return fileName;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                //        logger.warn("文件上传失败");
                throw new RuntimeException("文件上传失败");
            }
        }

        throw new RuntimeException("文件格式错误");

        //logger.info("文件上传成功");
        //   return dest;
    }

    //public String PathProcess(File file) {
    //    String path = file.getPath();
    //    String split[] = path.split("\\\\");
    //    int size = split.length;
    //    return split[size - 3] + "\\" + split[size - 2] + "\\" + split[size - 1];
    //}
//    public static void main(String[] args) {
//        File file = new File("C:\\Users\\thinkpad\\Desktop\\Backups\\All-in-One 03-05-2017\\表情暂存处\\6e70bef2b21193136340c61060380cd790238dc6.jpg");
//        FileInputStream input = null;
//        try {
//            input = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        MultipartFile multipartFile = null;
//        try {
//            multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        (new FileLoader()).load(multipartFile,request.getServletContext().getContextPath());
//    }
}
