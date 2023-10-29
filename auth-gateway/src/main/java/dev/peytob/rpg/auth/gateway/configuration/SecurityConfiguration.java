package dev.peytob.rpg.auth.gateway.configuration;

import dev.peytob.rpg.auth.gateway.configuration.properties.AdminProperties;
import dev.peytob.rpg.auth.gateway.service.AdminTokenUserDetailsProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableConfigurationProperties(AdminProperties.class)
public class SecurityConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    AuthenticationProvider preAuthenticatedAuthenticationProvider(AdminTokenUserDetailsProvider adminTokenUserDetailsProvider) {
        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(adminTokenUserDetailsProvider));
        return authenticationProvider;
    }

    @Bean
    RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter(AuthenticationManager authenticationManager) {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader(AUTHORIZATION_HEADER);
        requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(false);
        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return requestHeaderAuthenticationFilter;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter) throws Exception {
        return http
            .authorizeHttpRequests((authorize) -> authorize.requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui.html",
                "/error",
                "/actuator/**",
                "/*/auth/**",
                "/swagger-ui/**").permitAll())
            .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
            .sessionManagement(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .addFilter(requestHeaderAuthenticationFilter)
            .build();
    }
}
