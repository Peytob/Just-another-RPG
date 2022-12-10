package dev.peytob.rpg.engine.utils.reflection;

import org.junit.jupiter.api.Test;

import static dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils.resolveTypeArgument;
import static org.junit.jupiter.api.Assertions.*;

class GenericReflectionUtilsTest {

    @Test
    void commonClassGenericTypeCanBeFound() {
        Class<?> expectedIntegerClass = Integer.class;
        Class<?> actualFoundedClass = resolveTypeArgument(SomeIntegerGeneric.class, SomeGeneric.class);
        assertEquals(expectedIntegerClass, actualFoundedClass);
    }

    interface SomeGeneric<T> {
        T get();
    }

    static class SomeIntegerGeneric implements SomeGeneric<Integer> {
        @Override
        public Integer get() {
            return 0;
        }
    }
}