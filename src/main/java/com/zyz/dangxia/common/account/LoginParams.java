package com.zyz.dangxia.common.account;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("用户登陆所用资料")
public class LoginParams {
    private long phone;
    private String password;
}
