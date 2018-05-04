package com.zyz.dangxia.aspect;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DangxiaControllerAdvice {
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {}
//    //拦截错误 并返回json数据
//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public Map errorHandler(Exception ex) {
//        Map map = new HashMap();
//        map.put("code", 100);
//        map.put("msg", ex.getMessage());
//        return map;
//    }
}
