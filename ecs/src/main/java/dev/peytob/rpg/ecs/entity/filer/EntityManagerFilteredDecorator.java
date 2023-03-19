package dev.peytob.rpg.ecs.entity.filer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.entity.EntityManager;
import dev.peytob.rpg.ecs.exception.FilterAlreadyRegisteredException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EntityManagerFilteredDecorator implements EntityManager, FilterManager {

    private final EntityManager entityManager;

    private final Collection<Filter> filters;

    private final Multimap<Filter, Entity> filteredEntities;

    private final Multimap<Entity, Filter> entityFilters;

    public EntityManagerFilteredDecorator(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.filters = new ArrayList<>();
        this.filteredEntities = HashMultimap.create();
        this.entityFilters = HashMultimap.create();
    }

    @Override
    public boolean registerFilter(Filter filter) {
        if (filters.contains(filter)) {
            throw new FilterAlreadyRegisteredException(filter);
        }

        filters.add(filter);

        entityManager
                .getAll()
                .stream()
                .filter(filter::test)
                .forEach(entity -> registerFilterMatch(filter, entity));

        return true;
    }

    @Override
    public boolean removeFilter(Filter filter) {
        if (!filters.contains(filter)) {
            return false;
        }

        filters.remove(filter);
        clearFilterMatches(filter);

        return true;
    }

    @Override
    public Collection<Filter> getAllFilters() {
        return Collections.unmodifiableCollection(filters);
    }

    @Override
    public Collection<Entity> getEntitiesMatchingFilter(Filter filter) {
        return Collections.unmodifiableCollection(filteredEntities.get(filter));
    }

    @Override
    public boolean containsFilter(Filter filter) {
        return filters.contains(filter);
    }

    @Override
    public void clearFilters() {
        filters.clear();
        filteredEntities.clear();
        entityFilters.clear();
    }

    @Override
    public void register(Entity entity) {
        entityManager.register(entity);

        filters
                .stream()
                .filter(filter -> filter.test(entity))
                .forEach(filter -> registerFilterMatch(filter, entity));
    }

    @Override
    public boolean remove(Entity entity) {
        boolean isEntityRemoved = entityManager.remove(entity);

        if (!isEntityRemoved) {
            return false;
        }

        entityFilters
                .get(entity)
                .forEach(filter -> filteredEntities.remove(filter, entity));

        entityFilters.removeAll(entity);

        return true;
    }

    @Override
    public Collection<Entity> getAll() {
        return entityManager.getAll();
    }

    @Override
    public int getSize() {
        return entityManager.getSize();
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    private void registerFilterMatch(Filter filter, Entity entity) {
        filteredEntities.put(filter, entity);
        entityFilters.put(entity, filter);
    }

    private void clearFilterMatches(Filter filter) {
        filteredEntities
                .get(filter)
                .forEach(entity -> entityFilters.remove(entity, filter));

        filteredEntities.removeAll(filter);

    }
}
