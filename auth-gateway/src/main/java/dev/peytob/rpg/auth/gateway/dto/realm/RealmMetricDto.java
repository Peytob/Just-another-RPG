package dev.peytob.rpg.auth.gateway.dto.realm;

public interface RealmMetricDto {
    String getRealmName();

    Long getTotalUsersCount();

    Long getActiveUsersCount();
}
