package dev.peytob.rpg.engine.archetype;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public final class ComponentAbstractFactoryManager {

    private final Map<String, ComponentAbstractFactory<?, ?>> componentAbstractFactories;

    public ComponentAbstractFactoryManager(List<ComponentAbstractFactory<?, ?>> componentAbstractFactoriesList) {
        this.componentAbstractFactories = componentAbstractFactoriesList.stream()
                .collect(toMap(factory -> normalizeSerializationKey(factory.getSerializedName()), Function.identity()));
    }

    public ComponentAbstractFactory<?, ?> getAbstractFactory(String serializationKey) {
        return componentAbstractFactories.get(normalizeSerializationKey(serializationKey));
    }

    private String normalizeSerializationKey(String serializationKey) {
        return serializationKey.toLowerCase().strip();
    }
}
