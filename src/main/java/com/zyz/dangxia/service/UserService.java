package com.zyz.dangxia.service;

import com.zyz.dangxia.dto.UserDto;

public interface UserService {
    UserDto login(long phone,String password);
    UserDto register(long phone,String password);
}
