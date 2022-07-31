package com.study.kdy.chapter07.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

//@Order(2)
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

    @Around("execution(public * com.study.kdy.chapter07.service..*(..))")
    public Object pointCutExpressionDirect(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            var finish = System.nanoTime();
            var sig = joinPoint.getSignature();
            System.out.printf("[@Pointcut 생략]%s.%s(%s) 실행시간: %d ns\n", joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }

    @Around("CacheAspect.cacheTarget()")
    public Object cacheAspectPointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            var finish = System.nanoTime();
            var sig = joinPoint.getSignature();
            System.out.printf("[@CacheAspect 의 @Pointcut]%s.%s(%s) 실행시간: %d ns\n", joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }

}
