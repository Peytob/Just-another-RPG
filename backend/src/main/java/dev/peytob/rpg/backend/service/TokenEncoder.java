package dev.peytob.rpg.backend.service;

public interface TokenEncoder {

    String encode(String rawToken);
}
