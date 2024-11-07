package com.library.aspect;


import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for BookService methods
    @Pointcut("execution(* com.library.service..*(..))")
    public Object serviceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info(">>>>>>>>>LoggingAspect<<<<<<<<<<");
        long startTime = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder("KPI:");
        stringBuilder.append("[").append(joinPoint.getKind()).append("]\tfor").append(joinPoint.getSignature())
                .append("\twith Args : ").append("(").append(StringUtils.join(joinPoint.getArgs(),",")).append(")");
        stringBuilder.append("\ttook");
        Object returnValue =joinPoint.proceed();
        logger.info(stringBuilder.append(System.currentTimeMillis()-startTime).append("MS.").toString());
        return returnValue;


    }

}
