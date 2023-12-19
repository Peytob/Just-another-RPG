package dev.peytob.rpg.backend.service.security;

import dev.peytob.rpg.backend.service.TokenEncoder;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class Sha512TokenEncoder implements TokenEncoder {

    @Override
    public String encode(String rawToken) {
        return Sha512DigestUtils.shaHex(rawToken);
    }
}
