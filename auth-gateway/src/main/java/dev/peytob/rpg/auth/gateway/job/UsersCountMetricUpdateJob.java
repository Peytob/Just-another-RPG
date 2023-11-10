package dev.peytob.rpg.auth.gateway.job;

import dev.peytob.rpg.auth.gateway.dto.realm.RealmMetricDto;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static dev.peytob.rpg.auth.gateway.configuration.MetricsConfiguration.ACTIVE_USERS_PER_REALM_MULTI_GAUGE;
import static dev.peytob.rpg.auth.gateway.configuration.MetricsConfiguration.TOTAL_USERS_PER_REALM_MULTI_GAUGE;

@Component
@Slf4j
@RequiredArgsConstructor
public class UsersCountMetricUpdateJob {

    private static final Duration USER_ACTIVE_DURATION = Duration.ofDays(3);

    private final RealmCrudService realmCrudService;

    @Qualifier(TOTAL_USERS_PER_REALM_MULTI_GAUGE)
    private final MultiGauge totalUsersPerRealm;

    @Qualifier(ACTIVE_USERS_PER_REALM_MULTI_GAUGE)
    private final MultiGauge activeUsersPerRealm;

    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
    void updateUserCountMetrics() {
        log.debug("Updating realms metrics");

        Collection<RealmMetricDto> allRealmsMetrics = realmCrudService.getAllRealmsMetrics(USER_ACTIVE_DURATION);
        updateUsersPerRealmMetric(allRealmsMetrics);
        updateActiveUsersPerRealmMetric(allRealmsMetrics);
    }

    @SuppressWarnings("unchecked")
    private void updateUsersPerRealmMetric(Collection<RealmMetricDto> allRealmsMetrics) {
        Iterable<? extends MultiGauge.Row<?>> rows = allRealmsMetrics.stream()
            .map(metric -> MultiGauge.Row.of(buildRealmTags(metric), metric.getTotalUsersCount()))
            .toList();

        totalUsersPerRealm.register((Iterable<MultiGauge.Row<?>>) rows, true);
    }

    @SuppressWarnings("unchecked")
    private void updateActiveUsersPerRealmMetric(Collection<RealmMetricDto> allRealmsMetrics) {
        Iterable<? extends MultiGauge.Row<?>> rows = allRealmsMetrics.stream()
            .map(metric -> MultiGauge.Row.of(buildRealmTags(metric), metric.getActiveUsersCount()))
            .toList();

        activeUsersPerRealm.register((Iterable<MultiGauge.Row<?>>) rows, true);
    }

    private Tags buildRealmTags(RealmMetricDto metric) {
        return Tags.of("realmName", metric.getRealmName());
    }
}
