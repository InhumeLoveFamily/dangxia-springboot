package com.zyz.dangxia.controller;

import com.zyz.dangxia.dto.UserDto;
import com.zyz.dangxia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserDto login(long phone, String password) {
        return userService.login(phone, password);
    }

}
