package dev.peytob.rpg.engine.utils.reflection;

import org.springframework.core.GenericTypeResolver;

public enum GenericReflectionUtils {;

    public static Class<?> resolveTypeArgument(Class<?> cls, Class<?> genericClass) {
        return GenericTypeResolver
                .resolveTypeArgument(cls, genericClass);
    }
}
