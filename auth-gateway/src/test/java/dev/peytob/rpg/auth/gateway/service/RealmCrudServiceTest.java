package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.AuthGatewayContextTest;
import dev.peytob.rpg.auth.gateway.dto.realm.RealmMetricDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.test.util.TestEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class RealmCrudServiceTest extends AuthGatewayContextTest {

    @Autowired
    RealmCrudService realmCrudService;

    @Autowired
    TestEntityFactory testEntityFactory;

    @Test
    @Transactional
    void metricsCanBeGet() {
        final Duration activeUserBorder = Duration.of(1, ChronoUnit.DAYS);
        final Instant activeUserLoginAt = Instant.now().minus(6, ChronoUnit.HOURS);
        final Instant inactiveUserLoginAt = Instant.now().minus(32, ChronoUnit.HOURS);

        Realm firstRealm = testEntityFactory.createRealm("active1total3");
        testEntityFactory.createUser("1_firstRealm_active", firstRealm, activeUserLoginAt);
        testEntityFactory.createUser("2_firstRealm_inactive", firstRealm, inactiveUserLoginAt);
        testEntityFactory.createUser("3_firstRealm_inactive", firstRealm, inactiveUserLoginAt);

        Realm secondRealm = testEntityFactory.createRealm("active2total2");
        testEntityFactory.createUser("1_secondRealm_active", secondRealm, activeUserLoginAt);
        testEntityFactory.createUser("2_secondRealm_active", secondRealm, activeUserLoginAt);

        Realm thirdRealm = testEntityFactory.createRealm("active0total1");
        testEntityFactory.createUser("1_thirdRealm_inactive", thirdRealm, inactiveUserLoginAt);

        Realm fourthRealm = testEntityFactory.createRealm("active0total0");

        Collection<RealmMetricDto> allRealmsMetrics = realmCrudService.getAllRealmsMetrics(activeUserBorder);

        assertRealmMetric(allRealmsMetrics, firstRealm.getName(), 1, 3);
        assertRealmMetric(allRealmsMetrics, secondRealm.getName(), 2, 2);
        assertRealmMetric(allRealmsMetrics, thirdRealm.getName(), 0, 1);
        assertRealmMetric(allRealmsMetrics, fourthRealm.getName(), 0, 0);
    }

    private void assertRealmMetric(Collection<RealmMetricDto> metrics, String realmName, long activeUsers, int totalUsers) {
        RealmMetricDto realmMetricDto = metrics.stream()
            .filter(metric -> metric.getRealmName().equals(realmName))
            .findFirst()
            .orElseThrow();

        assertEquals(realmName, realmMetricDto.getRealmName());
        assertEquals(activeUsers, realmMetricDto.getActiveUsersCount());
        assertEquals(totalUsers, realmMetricDto.getTotalUsersCount());
    }
}