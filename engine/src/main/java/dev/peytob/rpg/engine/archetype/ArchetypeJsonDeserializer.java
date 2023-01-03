package dev.peytob.rpg.engine.archetype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

@Component
public final class ArchetypeJsonDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(ArchetypeJsonDeserializer.class);

    private static final Method CREATE_FACTORY_METHOD;

    static {
        CREATE_FACTORY_METHOD = GenericReflectionUtils.findMethod(ComponentAbstractFactory.class, "create", Object.class);

        if (null == CREATE_FACTORY_METHOD) {
            throw new RuntimeException("'Create' method in class 'ComponentAbstractFactory' not found!");
        }

        if (!CREATE_FACTORY_METHOD.getReturnType().equals(ComponentFactory.class)) {
            throw new RuntimeException("'Create' method in class 'ComponentAbstractFactory' should returns an component factory!");
        }
    }

    private final ObjectMapper objectMapper;

    private final ComponentAbstractFactoryManager componentAbstractFactoryManager;

    public ArchetypeJsonDeserializer(ObjectMapper objectMapper, ComponentAbstractFactoryManager componentAbstractFactoryManager) {
        this.objectMapper = objectMapper;
        this.componentAbstractFactoryManager = componentAbstractFactoryManager;
    }

    public Archetype deserialize(String json, Integer id, String textId) {
        JsonNode parsedJson;

        try {
            parsedJson = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new ArchetypeParsingException("Error while parsing JSON string for archetype " + textId, e);
        }

        return deserialize(parsedJson, id, textId);
    }

    private Archetype deserialize(JsonNode json, Integer id, String textId) {
        if (!json.isObject()) {
            throw new ArchetypeParsingException("The root node of the JSON tree must be object!");
        }

        ArchetypeBuilder archetypeBuilder = Archetypes.builder();

        for (var i = json.fields(); i.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = i.next();
            String key = entry.getKey();
            JsonNode node = entry.getValue();

            try {
                Optional<ComponentFactory<?>> componentFactory = createComponentFactory(key, node);
                componentFactory.ifPresentOrElse(
                        archetypeBuilder::addComponentFactory,
                        () -> logger.info("Component {} for archetype {} cant be loaded! Factory not found", key, textId));

            } catch (JsonProcessingException e) {
                throw new ArchetypeParsingException(
                        "In archetype " + textId + " component " + key + " can't be mapped from JSON", e);
            }
        }

        return archetypeBuilder.build(id, textId);
    }

    private Optional<ComponentFactory<?>> createComponentFactory(String key, JsonNode node) throws JsonProcessingException {
        ComponentAbstractFactory<?, ?> componentAbstractFactory = componentAbstractFactoryManager.getAbstractFactory(key);

        if (null == componentAbstractFactory) {
            return Optional.empty();
        }

        Object pattern = objectMapper.treeToValue(node, componentAbstractFactory.getPatternClass());
        ComponentFactory<?> componentFactory = (ComponentFactory<?>)
                GenericReflectionUtils.invokeMethod(CREATE_FACTORY_METHOD, componentAbstractFactory, pattern);

        return Optional.ofNullable(componentFactory);
    }
}
