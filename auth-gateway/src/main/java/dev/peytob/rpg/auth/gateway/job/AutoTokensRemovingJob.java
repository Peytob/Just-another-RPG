package dev.peytob.rpg.auth.gateway.job;

import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.service.TokenCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * TODO Make token remove query
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AutoTokensRemovingJob {

    private final TokenCrudService tokenCrudService;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void removeTokens() {
        log.debug("Removing expired tokens");

        Collection<Token> expiredTokens = tokenCrudService.findExpiredTokens();
        if (!expiredTokens.isEmpty()) {
            tokenCrudService.deleteTokens(expiredTokens);
            log.info("Removed {} expired tokens", expiredTokens.size());
        }
    }
}
