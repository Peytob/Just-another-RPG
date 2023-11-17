package dev.peytob.rpg.backend.entity;

import jakarta.persistence.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@SequenceGenerator(name = "stringId")
@RequiredArgsConstructor
public class StringIdGenerator implements IdentifierGenerator {

    // ZBase32 alphabet
    private final String GENERATION_ALPHABET = "ybndrfg8ejkmcpqxot1uwisza345h769";

    private final int GENERATION_ID_LENGTH = 16;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return RandomStringUtils.random(GENERATION_ID_LENGTH, GENERATION_ALPHABET);
    }
}
