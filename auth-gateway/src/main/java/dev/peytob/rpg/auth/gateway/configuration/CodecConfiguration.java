package dev.peytob.rpg.auth.gateway.configuration;

import org.apache.commons.codec.binary.Base32;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodecConfiguration {

    @Bean
    Base32 base32() {
        return new Base32();
    }
}
