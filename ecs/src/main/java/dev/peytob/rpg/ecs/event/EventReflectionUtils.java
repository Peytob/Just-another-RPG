package dev.peytob.rpg.ecs.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * These utilities are a (poor) replacement for Spring Core's ReflectionUtils. I don't want to have such a big
 * dependency for just a few methods.
 * This class should be removed later.
 */
enum EventReflectionUtils {;

    // TODO Resolve situations where the EventHandler<T> interface is implemented transitively.
    /**
     * Alternative for GenericTypeResolver::resolveTypeArgument method.
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Event> resolveHandlerEventType(EventHandler<? extends Event> eventHandler) {
        for (Type genericInterface : eventHandler.getClass().getGenericInterfaces()) {
            if (genericInterface instanceof ParameterizedType parameterizedInterface) {
                boolean isEventHandlerInterface = parameterizedInterface.getRawType().equals(EventHandler.class);

                if (!isEventHandlerInterface) {
                    continue;
                }

                Type genericType = parameterizedInterface.getActualTypeArguments()[0];
                return (Class<? extends Event>) genericType;
            }
        }

        throw new IllegalArgumentException("Event handler should directly implements EventHandler interface!");
    }
}
