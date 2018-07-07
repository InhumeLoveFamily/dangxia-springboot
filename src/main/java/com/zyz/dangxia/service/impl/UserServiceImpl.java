package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.base.FileLoader;
import com.zyz.dangxia.common.account.UserDto;
import com.zyz.dangxia.mapper.PictureMapper;
import com.zyz.dangxia.mapper.UserMapper;
import com.zyz.dangxia.model.PictureDO;
import com.zyz.dangxia.model.UserDO;
import com.zyz.dangxia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    FileLoader fileLoader;
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public UserDto login(long phone, String password) {
        UserDO user = userMapper.getByPhone(phone);
        if (user != null && user.getPassword().equals(password)) {
            return translate(user);
        }
        return null;
    }

    @Override
    public UserDto register(long phone, String password) {
        boolean alreadExsist = true;
        //老用户，修改密码
        UserDO user = userMapper.getByPhone(phone);
        //新用户，初始化信息
        if (user == null) {
            alreadExsist = false;
            user = new UserDO();
            user.setPhone(phone);
            user.setRegisterDate(new Date());
            user.setBalance(0D);
            user.setIconId(-1);
            user.setCredit(1);
            user.setIntegral(0D);
        }
        user.setPassword(password);
        int result = alreadExsist ? userMapper.updateByPrimaryKey(user) : userMapper.insert(user);
        return translate(user);
    }

    @Override
    public UserDto getInfo(int userId) {
        return translate(userMapper.selectByPrimaryKey(userId));
    }

    @Override
    @Transactional
    public int uploadIcon(MultipartFile file, String filePath, int userId) throws IOException {
        // TODO: 2018/6/9  检查文件是否符合要求

        //检查该用户是否存在
        UserDO user = userMapper.selectByPrimaryKey(userId);
        if (user == null) return -1;

        String contentType = file.getContentType();
        logger.info("contentType = {}", contentType);

        String fileName = file.getOriginalFilename();
        logger.info("fileName = {}", fileName);

        String accessPath = fileLoader.upload(file.getBytes(), filePath, fileName);
        if (accessPath == null) return -1;
        logger.info("accessPath = {}", accessPath);

        //上传成功之后将信息记录在数据库
        //先找之前有没有上传过头像，有则替换
        int iconId = user.getIconId();
        PictureDO picture = null;
        if (iconId != -1) {
            picture = pictureMapper.selectByPrimaryKey(iconId);
            if (picture != null) {
                logger.info("修改已有的记录");
                picture.setUrl(accessPath);
                pictureMapper.updateByPrimaryKey(picture);
            }
        }
        //说明以上的操作没有成功
        if (picture == null) {
            logger.info("新建一条记录");
            picture = new PictureDO();
            picture.setType(0);
            picture.setUrl(accessPath);
            picture.setDescription("用户" + userId + "的头像");
            picture.setContent(null);
            pictureMapper.insertAndGetId(picture);
            user.setIconId(picture.getId());
            userMapper.updateByPrimaryKey(user);
        }
        return 1;
    }

    @Override
    public ResponseEntity<?> loadIcon(int userId) {
        String path = pictureMapper.getPathByUserId(userId);
        logger.info("用户{}的头像路径 = {}", userId, path);
        if (path == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "image/png");
        return new ResponseEntity<>(resourceLoader.getResource("file:" + path),
                httpHeaders, HttpStatus.OK);
    }

    private UserDto translate(UserDO user) {
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
