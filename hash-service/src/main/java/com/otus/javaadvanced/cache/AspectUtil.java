package com.otus.javaadvanced.cache;

import org.aspectj.lang.Signature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public final class AspectUtil {
    public static final int BEFORE_HIGHEST_PRECEDENCE = Ordered.HIGHEST_PRECEDENCE + 1;

    private AspectUtil() {
    }

    //todo поиск не универсальный: при перегруженных методах могут быть неправильно выбраны методы
    @NotNull
    public static Method getMethod(@NotNull Signature signature, @Nullable Object[] args) {
        final Class declaringType = signature.getDeclaringType();
        Method method;
        if (args != null) {
            //  метод с конкретным типом аргументов
            method = ReflectionUtils.findMethod(declaringType,
                                                signature.getName(),
                                                Arrays.stream(args).map(x -> {
                                                    if (x != null) {
                                                        return x.getClass();
                                                    }
                                                    // почти наверно такого метода нет, тогда, если нет перегруженных, то найдём дальше по имени
                                                    return Object.class;
                                                }).toArray(Class[]::new)
            );

            if (method == null) {
                //  метод с базовым типом агрументов
                method = ReflectionUtils.findMethod(declaringType,
                                                    signature.getName(),
                                                    Arrays.stream(args).map(a -> Object.class).toArray(Class[]::new)
                );
            }
            if (method == null) {
                //  метод с любой сигнатурой
                method = ReflectionUtils.findMethod(declaringType,
                                                    signature.getName(),
                                                    null
                );
            }
        } else {
            method = ReflectionUtils.findMethod(declaringType, signature.getName());
        }
        return Objects.requireNonNull(method, "This method have to exist.");
    }
}
