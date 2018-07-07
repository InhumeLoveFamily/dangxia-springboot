package com.zyz.dangxia.service;

import com.zyz.dangxia.common.account.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserDto login(long phone,String password);
    UserDto register(long phone,String password);
    UserDto getInfo(int userId);

    int uploadIcon(MultipartFile multipartFile, String filePath, int userId) throws IOException;

    /**
     *
     * @param userId 用户id
     * @return 头像
     */
    ResponseEntity<?> loadIcon(int userId);
}
