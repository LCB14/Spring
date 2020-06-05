package com.lcb.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author changbao.li Date: 2019-10-30 Time: 15:14
 * @version $
 */
@Aspect
@Component
public class AspectJConfig {

    @Pointcut("execution(* *.test(..))")
    public void test(){}

    @Before("test()")
    public void beforeTest(){
        System.out.println("beforeInformTest");
    }

    @After("test()")
    public void afterTest(){
        System.out.println("afterInformTest");
    }

    @Around("test()")
    public Object arountTest(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("beforeArround");
        Object object = null;
        try{
            object = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("afterArround");
        return object;
    }
}
