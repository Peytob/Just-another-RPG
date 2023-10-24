package dev.peytob.rpg.auth.gateway.jpa;

import jakarta.persistence.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.Conversion;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
@SequenceGenerator(name = "base32id")
@RequiredArgsConstructor
public class Base32IdGenerator implements IdentifierGenerator {

    private final Base32 base32;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = new byte[16];
        Conversion.uuidToByteArray(uuid, bytes, 0, bytes.length);
        return base32.encodeAsString(bytes);
    }
}
