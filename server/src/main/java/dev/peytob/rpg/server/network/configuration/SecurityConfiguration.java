package dev.peytob.rpg.server.network.configuration;

import dev.peytob.rpg.server.network.rpc.security.TokenUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import static net.devh.boot.grpc.common.security.SecurityConstants.AUTHORIZATION_HEADER;

@Configuration
public class SecurityConfiguration {

    @Bean
    AuthenticationProvider preAuthenticatedAuthenticationProvider(TokenUserDetailsService tokenUserDetailsService) {
        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(tokenUserDetailsService));
        return authenticationProvider;
    }

    @Bean
    RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter(AuthenticationManager authenticationManager) {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader(AUTHORIZATION_HEADER.originalName());
        requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(false);
        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return requestHeaderAuthenticationFilter;
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
                    "/swagger-ui/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .addFilter(requestHeaderAuthenticationFilter)
            .build();
    }
}
