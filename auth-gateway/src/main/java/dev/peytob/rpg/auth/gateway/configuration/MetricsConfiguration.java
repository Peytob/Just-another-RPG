package dev.peytob.rpg.auth.gateway.configuration;

import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class MetricsConfiguration {

    public static final String TOTAL_USERS_PER_REALM_MULTI_GAUGE = "realm_users_total";

    public static final String ACTIVE_USERS_PER_REALM_MULTI_GAUGE = "realm_active_users_total";

    private final MeterRegistry meterRegistry;

    private final RealmCrudService realmCrudService;

    @PostConstruct
    void setUpCustomMetrics() {
        log.info("Registering realm count metric");
        Gauge
            .builder("realm_total", realmCrudService::getRealmCount)
            .description("Total realms count")
            .register(meterRegistry);
    }

    @Bean(TOTAL_USERS_PER_REALM_MULTI_GAUGE)
    MultiGauge usersPerRealmMultiGauge() {
        log.info("Registering users count per realm metric");

        return MultiGauge
            .builder(TOTAL_USERS_PER_REALM_MULTI_GAUGE)
            .description("Total registered users in realm count")
            .register(meterRegistry);
    }

    @Bean(ACTIVE_USERS_PER_REALM_MULTI_GAUGE)
    MultiGauge activeUsersPerRealmMultiGauge() {
        log.info("Registering active users count per realm metric");

        return MultiGauge
            .builder(ACTIVE_USERS_PER_REALM_MULTI_GAUGE)
            .description("Total active users in realm count")
            .register(meterRegistry);
    }
}
