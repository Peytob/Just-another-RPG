package dev.peytob.rpg.engine.loader.archetype;

import dev.peytob.rpg.engine.loader.archetype.componentFactory.ComponentAbstractFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

@Component
public final class ComponentAbstractFactoryManager {

    private final Map<String, ComponentAbstractFactory<?, ?>> componentAbstractFactories;

    public ComponentAbstractFactoryManager(Collection<ComponentAbstractFactory<?, ?>> componentAbstractFactoriesList) {
        this.componentAbstractFactories = componentAbstractFactoriesList.stream()
                .collect(toUnmodifiableMap(factory -> normalizeSerializationKey(factory.getSerializedName()), Function.identity()));
    }

    public ComponentAbstractFactory<?, ?> getAbstractFactory(String serializationKey) {
        return componentAbstractFactories.get(normalizeSerializationKey(serializationKey));
    }

    private String normalizeSerializationKey(String serializationKey) {
        return serializationKey.toLowerCase().strip();
    }
}
