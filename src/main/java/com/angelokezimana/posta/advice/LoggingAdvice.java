package com.angelokezimana.posta.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

    Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);
    private final ObjectMapper objectMapper;

    public LoggingAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut(value = "execution(public * com.angelokezimana.posta.controller..*(..)) " +
            "|| execution(public * com.angelokezimana.posta.service..*(..))")
    public void myPointCut() {    }

    @Around("myPointCut()")
    public Object ApplicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object[] args = joinPoint.getArgs();

        logger.info("Method invoked: {}.{}() => Args:{}", className, methodName, safeToJson(args));

        Object result = joinPoint.proceed();

        logger.info("{}.{}() => Response:{}", className, methodName, safeToJson(result));

        return result;
    }

    private String safeToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.warn("Serialization failed for {}: {}",
                    obj.getClass().getSimpleName(), e.getMessage());
            return "[Non-serializable object: " + obj.getClass().getSimpleName() + "]";
        }
    }
}
