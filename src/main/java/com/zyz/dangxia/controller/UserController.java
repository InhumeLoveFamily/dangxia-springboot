package com.zyz.dangxia.controller;

import com.zyz.dangxia.common.account.LoginParams;
import com.zyz.dangxia.common.account.UserDto;
import com.zyz.dangxia.constant.DangxiaConstants;
import com.zyz.dangxia.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api("账户相关")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiImplicitParams({
            /*参数是从表单获取的，而表单的数据实质上是放在requst body中的,在这里paramType填form更加合适，body代表数据是以流的形式提交的
            * name-接受的参数名，value-参数名的意义*/
            @ApiImplicitParam(paramType = "form",name = DangxiaConstants.LOGIN_ACCOUNT,value = "account number(phone)",dataType = "Long",defaultValue = DangxiaConstants.BLANK),
            @ApiImplicitParam(paramType = "form",name = DangxiaConstants.LOGIN_PASSWORD,value = "account password",dataType = "String",defaultValue = DangxiaConstants.BLANK)
    })
    @ApiOperation("登陆接口")
    @PostMapping("/login")
    public UserDto login(long phone,String password) {
        UserDto user = userService.login(/*loginParams.getPhone(), loginParams.getPassword()*/phone,password);
        if(user == null) {
            logger.info(phone+"登录失败");
            throw new RuntimeException("登录失败");
        }
        return user;
    }

    @PostMapping("/register")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form",name = DangxiaConstants.LOGIN_ACCOUNT,value = "account number(phone)",dataType = "Long",defaultValue = DangxiaConstants.BLANK),
            @ApiImplicitParam(paramType = "form",name = DangxiaConstants.LOGIN_PASSWORD,value = "account password",dataType = "String",defaultValue = DangxiaConstants.BLANK)
    })
    @ApiOperation("注册接口")
    public UserDto register(long phone,String password) {
        return userService.register(phone,password);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",name = DangxiaConstants.USER_ID,value = "user_id",dataType = "int",defaultValue = DangxiaConstants.BLANK)
    })
    @GetMapping("/{userId}")
    @ApiOperation("获取他人信息")
    public UserDto getInfo(@PathVariable("userId") int userId) {
        return userService.getInfo(userId);
    }

}
