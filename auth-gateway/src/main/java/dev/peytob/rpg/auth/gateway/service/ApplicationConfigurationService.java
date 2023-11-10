package dev.peytob.rpg.auth.gateway.service;

/**
 * Pretty facade for all application configuration
 */
public interface ApplicationConfigurationService {

    boolean isRegistrationByUserEnabled();

    boolean isLongTimeTokensEnabled();
}
