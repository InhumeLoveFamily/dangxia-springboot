package com.zyz.dangxia.controller;

import com.zyz.dangxia.dto.UserDto;
import com.zyz.dangxia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/login")
    public UserDto login(long phone, String password) {
        UserDto user = userService.login(phone, password);
        if(user == null) {
            logger.info(phone+":"+password+"登录失败");
        }
        return user;
    }

    @PostMapping("/register")
    public UserDto register(long phone,String password) {
        return userService.register(phone,password);
    }

    @GetMapping("")
    public UserDto getInfo(@RequestParam("userId") int userId) {
        return userService.getInfo(userId);
    }

}
