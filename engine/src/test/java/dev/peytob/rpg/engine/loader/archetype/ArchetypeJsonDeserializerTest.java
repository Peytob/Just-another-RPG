package dev.peytob.rpg.engine.loader.archetype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.engine.ContextRpgEngineTest;
import dev.peytob.rpg.engine.component.TestPositionComponent;
import dev.peytob.rpg.engine.component.TestTextureComponentPattern;
import dev.peytob.rpg.engine.loader.archetype.componentFactory.ComponentAbstractFactory;
import dev.peytob.rpg.engine.loader.archetype.componentFactory.ComponentFactory;
import dev.peytob.rpg.engine.resource.Archetype;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArchetypeJsonDeserializerTest extends ContextRpgEngineTest {

    @Autowired
    ArchetypeJsonDeserializer archetypeJsonDeserializer;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void isArchetypeDeserializesCorrectly() throws JsonProcessingException {
        final String textId = "some_npc";
        final Integer id = 2;
        final Map<String, Object> serializedArchetypeMap = Map.of(
                "position", new TestPositionComponent(1, -5, 7),
                "texture", new TestTextureComponentPattern("hero")
        );

        final String json = objectMapper.writeValueAsString(serializedArchetypeMap);

        Archetype archetype = archetypeJsonDeserializer.deserialize(json, id, textId);

        assertEquals(id, archetype.id());
        assertEquals(textId, archetype.textId());
        assertEquals(1, archetype.componentFactories().size());
    }

    @TestConfiguration
    public static class TestComponentsAbstractFactories {

        @Bean
        ComponentAbstractFactory<TestPositionComponent, TestPositionComponent> testPositionComponent() {
            return new ComponentAbstractFactory<>() {
                @Override
                public ComponentFactory<TestPositionComponent> create(TestPositionComponent pattern) {
                    return () -> new TestPositionComponent(pattern.getX(), pattern.getY(), pattern.getZ());
                }

                @Override
                public Class<TestPositionComponent> getPatternClass() {
                    return TestPositionComponent.class;
                }

                @Override
                public String getSerializedName() {
                    return "position";
                }
            };
        }
    }
}