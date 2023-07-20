package dev.peytob.rpg.client.module.test.context.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.module.base.service.networking.HelloService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@IncludeInAllStates
@Component
public class TestHelloSystem implements System {

    HelloService helloService;

    boolean isHello = false;

    public TestHelloSystem(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public void execute(EcsContext context) {

        if (!isHello) {
            String hello = helloService.hello();
            java.lang.System.out.println(hello);
            isHello = true;
        }
    }
}
