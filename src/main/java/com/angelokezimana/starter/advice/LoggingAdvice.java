/**
 * Created By angelokezimana
 * Date: 1/12/2024
 * Time: 8:30 AM
 */

package com.angelokezimana.starter.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
public class LoggingAdvice {

    Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);
    private final ObjectMapper objectMapper;

    public LoggingAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut(value = "execution(public * com.angelokezimana.starter.controller..*(..)) " +
            "|| execution(public * com.angelokezimana.starter.service..*(..))")
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
            ObjectMapper customMapper = objectMapper.copy();
            customMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            // Custom filter to exclude byte[] fields
            SimpleModule module = new SimpleModule();
            module.addSerializer(byte[].class, new JsonSerializer<byte[]>() {
                @Override
                public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString("[hidden]");
                }
            });
            customMapper.registerModule(module);

            return customMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.warn("Serialization failed for {}: {}",
                    obj.getClass().getSimpleName(), e.getMessage());
            return "[Non-serializable object: " + obj.getClass().getSimpleName() + "]";
        }
    }
}
