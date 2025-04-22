/**
 * Created By angelokezimana
 * Date: 1/12/2024
 * Time: 8:30 AM
 */

package com.angelokezimana.starter.common.aop.logging;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper;

    public LoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut(value = "execution(public * com.angelokezimana.starter..*.web..*(..)) " +
            "|| execution(public * com.angelokezimana.starter..*.service..*(..))")
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

            // Custom filter to exclude sensitive fields like 'password'
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("password");
            FilterProvider filters = new SimpleFilterProvider()
                    .addFilter("sensitiveFilter", filter);

            // Apply the filter to all objects
            customMapper.setFilterProvider(filters);
            customMapper.addMixIn(Object.class, SensitiveDataFilterMixin.class);

            // Custom serializer for byte[] fields
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

    // Mix-in to apply the filter to all objects
    @JsonFilter("sensitiveFilter")
    private interface SensitiveDataFilterMixin {}
}
