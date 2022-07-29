package com.study.kdy.chapter07.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
public class ExeTimeAspect {

    @Pointcut("execution(public * com.study.kdy.chapter07.service..*(..))")
    private void publicTarget() {
    }

    @Around("publicTarget()")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            var finish = System.nanoTime();
            var sig = joinPoint.getSignature();
            System.out.printf("%s.%s(%s) 실행시간: %d ns\n", joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }

}
