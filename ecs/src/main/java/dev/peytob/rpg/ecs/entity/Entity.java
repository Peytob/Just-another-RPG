package dev.peytob.rpg.ecs.entity;

import dev.peytob.rpg.ecs.component.Component;

import java.util.Collection;

public interface Entity {

    String getId();

    Collection<Component> getComponents();

    Collection<Class<? extends Component>> getComponentsTypes();

    <T extends Component> T getComponent(Class<T> componentClass);

    <T extends Component> T removeComponent(Class<T> componentClass);

    void bindComponent(Component component);

    boolean isEmpty();

    boolean isAlive();
}
