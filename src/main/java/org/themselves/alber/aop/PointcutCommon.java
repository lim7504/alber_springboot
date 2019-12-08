package org.themselves.alber.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutCommon {

    @Pointcut("execution(* org.themselves.alber..*Impl.*(..))")
    public void allPointcut() {}

    @Pointcut("execution(* org.themselves.alber..*Impl.get*(..))")
    public void getPointcut() {}


}
