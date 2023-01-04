package dev.peytob.rpg.engine.utils.reflection;

import org.springframework.core.GenericTypeResolver;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public enum GenericReflectionUtils {;

    public static Class<?> resolveTypeArgument(Class<?> cls, Class<?> genericClass) {
        return GenericTypeResolver
                .resolveTypeArgument(cls, genericClass);
    }

    public static Method findMethod(Class<?> cls, String name, Class<?>... args) {
        return ReflectionUtils.findMethod(cls, name, args);
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        return ReflectionUtils.invokeMethod(method, target, args);
    }
}
