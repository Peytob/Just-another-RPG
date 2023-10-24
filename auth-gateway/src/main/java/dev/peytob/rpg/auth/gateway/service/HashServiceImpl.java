package dev.peytob.rpg.auth.gateway.service;

import org.apache.commons.codec.digest.Sha2Crypt;
import org.springframework.stereotype.Service;

@Service
public class HashServiceImpl implements HashService {

    private static final String STATIC_PASSWORD_SALT = "somePasswordStaticSalt";
    private static final String STATIC_TOKEN_SALT = "someTokenStaticSalt";

    @Override
    public String hashPasswordString(String password) {
        return Sha2Crypt.sha256Crypt(password.getBytes(), STATIC_PASSWORD_SALT);
    }

    @Override
    public String hashTokenString(String token) {
        return Sha2Crypt.sha256Crypt(token.getBytes(), STATIC_TOKEN_SALT);
    }
}
