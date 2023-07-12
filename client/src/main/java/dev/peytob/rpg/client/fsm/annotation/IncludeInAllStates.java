package dev.peytob.rpg.client.fsm.annotation;

import dev.peytob.rpg.client.fsm.EngineState;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IncludeInAllStates {

    Class<? extends EngineState>[] exclude() default {};

    int order() default 0;
}
