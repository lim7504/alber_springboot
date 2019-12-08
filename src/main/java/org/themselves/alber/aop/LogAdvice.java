package org.themselves.alber.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAdvice {

    @Before("PointcutCommon.allPointcut()")
    public void printLog() {
        System.out.println("[공통로그] 비지니스 로직 수행 전 동작");
    }

}