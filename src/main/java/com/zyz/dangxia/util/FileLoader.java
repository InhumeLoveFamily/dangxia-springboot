package com.zyz.dangxia.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileLoader {

    Logger logger = LoggerFactory.getLogger(FileLoader.class);

    /**
     * @param file     文件的字节码
     * @param filePath 文件存放的绝对路径
     * @param fileName 文件的名称，包含后缀名
     * @return 文件的访问路径
     */
    public String upload(byte[] file, String filePath, String fileName) throws IOException {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
        out.write(file);
        out.flush();
        out.close();
        return filePath + "\\" + fileName;//在数据库中是以反斜杠\的形式存在的，很迷
    }

    public byte[] load(String absolutePath) throws IOException {
        File file = new File(absolutePath);
//        if(!file.exists()) {
//            logger.info("{}路径下的文件并不存在",absolutePath);
//            return null;
//        }
        InputStream in = new FileInputStream(file);
        byte[] body = new byte[in.available()];
        in.close();
        return body;
    }

}
