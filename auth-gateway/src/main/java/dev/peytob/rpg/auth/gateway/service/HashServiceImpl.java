package dev.peytob.rpg.auth.gateway.service;

import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class HashServiceImpl implements HashService {

    private static final String STATIC_PASSWORD_SALT = "RF8TU3dvbL";
    private static final String STATIC_TOKEN_SALT = "ZPXKlgrqfk";

    @Override
    public String hashPasswordString(String password) {
        return Sha512DigestUtils.shaHex(password + STATIC_PASSWORD_SALT);
    }

    @Override
    public String hashTokenString(String token) {
        return Sha512DigestUtils.shaHex(token + STATIC_TOKEN_SALT);
    }
}
