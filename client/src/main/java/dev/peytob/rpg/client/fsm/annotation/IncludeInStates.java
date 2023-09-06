package dev.peytob.rpg.client.fsm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IncludeInStates {

    IncludeInState[] states() default {};
}

