package dev.peytob.rpg.ecs.entity.filer;

import dev.peytob.rpg.ecs.entity.Entity;

import java.util.Collection;

public interface FilterManager {

    boolean registerFilter(Filter filter);

    boolean removeFilter(Filter filter);

    Collection<Filter> getAllFilters();

    Collection<Entity> getEntitiesMatchingFilter(Filter filter);

    boolean containsFilter(Filter filter);

    void clearFilters();
}
