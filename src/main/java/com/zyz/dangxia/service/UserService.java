package com.zyz.dangxia.service;

import com.zyz.dangxia.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserDto login(long phone,String password);
    UserDto register(long phone,String password);
    UserDto getInfo(int userId);

    int uploadIcon(MultipartFile multipartFile, String filePath, int userId) throws IOException;
}
