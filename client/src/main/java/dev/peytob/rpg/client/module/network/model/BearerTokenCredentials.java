package dev.peytob.rpg.client.module.network.model;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.concurrent.Executor;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class BearerTokenCredentials extends CallCredentials {

    private static final Metadata.Key<String> AUTHENTIFICATION_KEY = Metadata.Key.of("authentication", ASCII_STRING_MARSHALLER);

    private final Metadata metadata;

    public BearerTokenCredentials(String value) {
        this.metadata = new Metadata();
        this.metadata.put(AUTHENTIFICATION_KEY, value);
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(() -> {
            try {
                applier.apply(metadata);
            } catch (Throwable e) {
                applier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {

    }
}
