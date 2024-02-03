package dev.peytob.rpg.core.loading.service.loader;

import dev.peytob.rpg.core.loading.service.provider.RawResourceDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractResourceDataLoader<R, T> implements ResourceDataLoader<T> {

    private final Collection<RawResourceDataProvider<R>> rawResourceDataProviders;

    @Override
    public Collection<T> loadResources() {
        return rawResourceDataProviders.stream()
            .map(resourceDataProvider -> {
                log.info("Trying to load raw resources by {} provider", resourceDataProvider.getClass().getSimpleName());
                return resourceDataProvider.loadResourcesRawData();
            })
            .flatMap(Collection::stream)
            .map(this::mapToResource)
            .toList();
    }

    protected abstract T mapToResource(R rawResource);
}
