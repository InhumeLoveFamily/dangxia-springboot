package com.zyz.dangxia.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DangxiaAspect {
//    //pointcut 切入点，是对需要进行aop代理的连接点的统一描述
//    //within without 是用来修饰一整个类的 需要写出完整的包名
//    //execution 用来修饰某个方法 需要写出方法的权限、返回值、方法名等属性
//    @Pointcut("within(com.zyz.dangxia.controller..*) &&" +
//            "!execution(public * com.zyz.dangxia.controller.UserController.login(..))")
//    public void executionService(){}
//
//    @Before("executionService()")
//    public void doBefore(JoinPoint joinPoint) {
//        System.out.println("before of "+joinPoint.getSignature().getName());
//    }
//
//    @AfterReturning(value = "executionService()",returning = "keys")
//    public void doAfterReturning(JoinPoint joinPoint,Object keys) {
//        System.out.println("after returning "+joinPoint.getSignature().getName());
//    }
//
////    @AfterThrowing(value = "executionService()",throwing = "e")
////    public void doAfterThrowing(JoinPoint joinPoint,Throwable e) {
////        System.out.println("after throwing "+joinPoint.getSignature().getName()+" : " +
////                e.getMessage());
////    }

}
