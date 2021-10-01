package com.geekbrains.webapp.configs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Data
public class UselessAspect {
    private Map<String, Long> durations;
    private static final String ORDER_SERVICE = "OrderService";
    private static final String PRODUCT_SERVICE = "ProductService";
    private static final String USER_SERVICE = "UserService";

    public UselessAspect() {
        durations = new HashMap<>();
        durations.put(ORDER_SERVICE, 0l);
        durations.put(PRODUCT_SERVICE, 0l);
        durations.put(USER_SERVICE, 0l);
    }

    @Around("execution(public * com.geekbrains.webapp.services.OrderService.*(..))")
    public Object orderServiceExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("start profiling");
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        durations.put(ORDER_SERVICE, durations.get(ORDER_SERVICE) + duration);
        return out;
    }
    @Around("execution(public * com.geekbrains.webapp.services.UserService.*(..))")
    public Object userServiceExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("start profiling");
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        durations.put(USER_SERVICE, durations.get(USER_SERVICE) + duration);
        return out;
    }
    @Around("execution(public * com.geekbrains.webapp.services.ProductService.*(..))")
    public Object productServiceExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("start profiling");
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        durations.put(PRODUCT_SERVICE, durations.get(PRODUCT_SERVICE) + duration);
        return out;
    }
}
