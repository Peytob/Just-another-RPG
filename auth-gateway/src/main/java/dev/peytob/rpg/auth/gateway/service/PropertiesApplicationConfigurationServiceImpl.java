package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.configuration.properties.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(AuthProperties.class)
@RequiredArgsConstructor
public class PropertiesApplicationConfigurationServiceImpl implements ApplicationConfigurationService {

    private final AuthProperties authProperties;

    @Override
    public boolean isRegistrationByUserEnabled() {
        return authProperties.registrationByUserEnabled();
    }

    @Override
    public boolean isLongTimeTokensEnabled() {
        return authProperties.longTimeTokensEnabled();
    }
}


