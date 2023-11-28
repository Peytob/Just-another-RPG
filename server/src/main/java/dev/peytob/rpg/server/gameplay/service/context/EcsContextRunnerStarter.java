package dev.peytob.rpg.server.gameplay.service.context;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Service
@Slf4j
public class EcsContextRunnerStarter {

    private final ExecutorService executorService;

    private final Collection<EcsContextRunner> ecsContextRunners;

    @PostConstruct
    void startContextsExecuting() {
        log.info("Starting {} ecs contexts executing threads", ecsContextRunners.size());
        ecsContextRunners.forEach(executorService::execute);
        log.info("Ecs contexts threads was started");
    }
}
