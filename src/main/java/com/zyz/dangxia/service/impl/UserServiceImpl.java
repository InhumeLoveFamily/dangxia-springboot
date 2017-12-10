package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.UserDto;
import com.zyz.dangxia.entity.User;
import com.zyz.dangxia.repository.UserRepository;
import com.zyz.dangxia.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public UserDto login(long phone, String password) {
        User user = userRepository.findByPhone(phone);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return translate(user);
            }
        }
        return null;
    }

    @Override
    public UserDto register(long phone, String password) {
        //老用户，修改密码
        User user  = userRepository.findByPhone(phone);
        //新用户，初始化信息
        if(user == null) {
            user = new User();
            user.setPhone(phone);
            user.setRegisterDate(new Date());
            user.setBalance(0);
            user.setIconId(-1);
            user.setCredit(1);
            user.setIntegral(0);
        }
        user.setPassword(password);
        user = userRepository.saveAndFlush(user);
        return translate(user);
    }

    private UserDto translate(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setRegisterDate(format.format(user.getRegisterDate()));
        userDto.setPassword("");
        return userDto;
    }
}
