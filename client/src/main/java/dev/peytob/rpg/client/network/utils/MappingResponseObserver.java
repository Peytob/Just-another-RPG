package dev.peytob.rpg.client.network.utils;

import io.grpc.stub.StreamObserver;
import org.springframework.core.convert.converter.Converter;

public class MappingResponseObserver<O, M> implements StreamObserver<O> {

    private final StreamObserver<M> originalStream;

    private final Converter<O, M> converter;

    public MappingResponseObserver(StreamObserver<M> originalStream, Converter<O, M> converter) {
        this.originalStream = originalStream;
        this.converter = converter;
    }

    @Override
    public void onNext(O value) {
        M convertedValue = converter.convert(value);
        originalStream.onNext(convertedValue);
    }

    @Override
    public void onError(Throwable t) {
        originalStream.onError(t);
    }

    @Override
    public void onCompleted() {
        originalStream.onCompleted();
    }
}
