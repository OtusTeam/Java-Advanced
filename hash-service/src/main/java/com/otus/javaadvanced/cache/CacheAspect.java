package com.otus.javaadvanced.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Aspect
public class CacheAspect {

    private static final Map<Signature, Cache<Object, Object>> CACHE = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.otus.javaadvanced.cache.Cachable)")
    public void cachable() {
    }

    @Around("cachable()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        val cachePerJoinPoint = getCache(point);
        val key = buildKey(point);

        try {
            return cachePerJoinPoint.get(key,
                                         () -> {
                                             final Object result;
                                             try {
                                                 result = point.proceed();
                                             } catch (Throwable throwable) {
                                                 throw new RuntimeException(throwable);
                                             }
                                             return result;
                                         }
            );

        } catch (RuntimeException e) {
            if (e.getCause() != null) {
                throw e.getCause();
            }
            throw e;
        }
    }

    @NotNull
    private static Cache<Object, Object> getCache(@NotNull ProceedingJoinPoint point) {
        return CACHE.computeIfAbsent(point.getSignature(), key -> {
            final Method method = AspectUtil.getMethod(point.getSignature(), point.getArgs());
            final Cachable cachedResult = method.getAnnotation(Cachable.class);

            final CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();
            final int maximumSize = cachedResult.maximumSize();
            if (maximumSize != Cachable.UNSET) {
                builder.maximumSize(maximumSize);
            }
            final long expireAfterWriteDuration = cachedResult.expireAfterWriteDuration();
            if (expireAfterWriteDuration != Cachable.UNSET) {
                final TimeUnit tu = cachedResult.expireAfterWriteTimeUnit();
                builder.expireAfterWrite(expireAfterWriteDuration, tu);
            }
            final long expireAfterAccessDuration = cachedResult.expireAfterAccessDuration();
            if (expireAfterAccessDuration != Cachable.UNSET) {
                final TimeUnit tu = cachedResult.expireAfterAccessTimeUnit();
                builder.expireAfterAccess(expireAfterAccessDuration, tu);
            }

            final int concurrencyLevel = cachedResult.concurrencyLevel();
            if (concurrencyLevel != Cachable.UNSET) {
                builder.concurrencyLevel(concurrencyLevel);
            }

            return builder.removalListener(notification -> log
                    .info("Cache has expired: key={}, value={}, cause={}",
                          notification.getKey(),
                          notification.getValue(),
                          notification.getCause()
                    )).build();
        });
    }

    @Nullable
    private static Object buildKey(@NotNull ProceedingJoinPoint point) {
        final Object[] args = point.getArgs();

        // NOTE Нужен List потому что у массива не переопределен equals/hashCode
        return args.length == 0 ? StringUtils.EMPTY : args.length == 1 ? args[0] : Arrays.asList(args);
    }
}
