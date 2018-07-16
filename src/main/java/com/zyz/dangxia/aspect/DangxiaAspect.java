package com.zyz.dangxia.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.google.common.base.Stopwatch;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
public class DangxiaAspect {

    private static final ValueFilter JSON_FILTER = (object, name, value) -> {
        if (value instanceof List) {
            List list = (List) value;
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        return value;
    };
    //pointcut 切入点，是对需要进行aop代理的连接点的统一描述
    //within without 是用来修饰一整个类的 需要写出完整的包名
    //execution 用来修饰某个方法 需要写出方法的权限、返回值、方法名等属性
    @Pointcut("within(com.zyz.dangxia.controller..*) &&" +
            "!execution(public * com.zyz.dangxia.controller.UserController.login(..))")
    public void executionService(){}

//    @Before("executionService()")
//    public void doBefore(JoinPoint joinPoint) {
//        System.out.println("before of "+joinPoint.getSignature().getName());
//    }
//
//    @AfterReturning(value = "executionService()",returning = "keys")
//    public void doAfterReturning(JoinPoint joinPoint,Object keys) {
//        System.out.println("after returning "+joinPoint.getSignature().getName());
//    }

    /**
     *为什么在aop中只是打印参数和统计运行事件，而不把登录拦截和参数验证的业务放入其中？
     * 可能是因为从join point中取出的参数，难以确定类型和到底是哪个参数，给校验带来不便
     */
    @Around("executionService()")
    public Object logArgs(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String desc = getDesc(method);
        List<Object>  objectList = getParams(joinPoint);
        Stopwatch sw = Stopwatch.createStarted();
        Object result = null;
        log.info("#开始执行#{}#, method=#{}#, params={}", desc, method.getName(), toJson(objectList));
        try {
            result = joinPoint.proceed();
        } finally {
            sw.stop();
            log.info(String.format("【%s 请求结束】 总共耗时:%s 毫秒", desc, sw.elapsed(TimeUnit.MILLISECONDS)));
        }
        log.info("#执行完成#{}#, method=#{}#, result={}", desc, method.getName(), toJson(result));
        return result;
    }

    private String getDesc(Method method) {
        ApiOperation apiOperation = AnnotationUtils.findAnnotation(method,ApiOperation.class);
        return apiOperation == null ? method.getName() : apiOperation.value();
    }

    private List<Object> getParams(ProceedingJoinPoint joinPoint) {
        return Stream.of(joinPoint.getArgs())
                .filter(arg -> !(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse))
                .map(arg ->{
                    if(arg instanceof List) {
                        List list = (List)arg;
                        if(!CollectionUtils.isEmpty(list)) {
                            return new DataHolder(list.size(),list.get(0));
                        }
                    }
                    return arg;
                })
                .collect(Collectors.toList());
    }

    private Object toJson(Object o) {
        String rst = JSON.toJSONString(o, JSON_FILTER);
        return rst.length() > 2000 ? rst.substring(0,1999) : rst;
    }


//    @AfterThrowing(value = "executionService()",throwing = "e")
//    public void doAfterThrowing(JoinPoint joinPoint,Throwable e) {
//        System.out.println("after throwing "+joinPoint.getSignature().getName()+" : " +
//                e.getMessage());
//    }

    @AllArgsConstructor
    @Data
    private class DataHolder{
        int size;
        Object data;
    }
}
