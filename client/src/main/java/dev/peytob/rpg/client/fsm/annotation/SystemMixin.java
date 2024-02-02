package dev.peytob.rpg.client.fsm.annotation;

import dev.peytob.rpg.ecs.system.System;

public interface SystemMixin {

    Class<? extends System> getTargetSystem();
}
