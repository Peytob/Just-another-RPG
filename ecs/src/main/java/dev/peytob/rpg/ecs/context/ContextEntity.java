package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.Component;
import dev.peytob.rpg.ecs.entity.Entity;

import java.util.Collection;

final class ContextEntity implements Entity {

    private final MutableEcsContext context;

    private final Entity entity;

    ContextEntity(MutableEcsContext context, Entity entity) {
        this.context = context;
        this.entity = entity;
    }

    @Override
    public String getId() {
        return entity.getId();
    }

    @Override
    public Collection<Component> getComponents() {
        return entity.getComponents();
    }

    @Override
    public Collection<Class<? extends Component>> getComponentsTypes() {
        return entity.getComponentsTypes();
    }

    @Override
    public <T extends Component> T getComponent(Class<T> componentClass) {
        return entity.getComponent(componentClass);
    }

    @Override
    public <T extends Component> T removeComponent(Class<T> componentClass) {
        T component = getComponent(componentClass);
        context.getComponentManager().remove(component);
        return entity.removeComponent(componentClass);
    }

    @Override
    public void bindComponent(Component component) {
        context.getComponentManager().register(component);
        entity.bindComponent(component);
    }

    @Override
    public boolean isEmpty() {
        return entity.isEmpty();
    }

    @Override
    public boolean isAlive() {
        return context.getUnmodifiableEntityManager().getById(getId()) != null;
    }
}
