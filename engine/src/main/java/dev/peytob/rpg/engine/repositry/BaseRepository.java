package dev.peytob.rpg.engine.repositry;

import com.google.common.collect.ArrayListMultimap;
import dev.peytob.rpg.engine.resource.Resource;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Just wraps Map class.
 */
public abstract class BaseRepository<R extends Resource> implements Repository<R> {

    private final Map<String, R> resourcesById = new ConcurrentHashMap<>();

    private final Collection<RepositoryIndex<?>> repositoryIndices = new CopyOnWriteArrayList<>();

    @Override
    public final Optional<R> getById(String textId) {
        return Optional.ofNullable(resourcesById.get(textId));
    }

    @Override
    public final Collection<R> getAll() {
        return resourcesById.values();
    }

    @Override
    public final Integer getCount() {
        return resourcesById.size();
    }

    @Override
    public final boolean contains(String id) {
        return resourcesById.containsKey(id);
    }

    @Override
    public final boolean append(R resource) {
        if (contains(resource)) {
            return false;
        }

        resourcesById.put(resource.id(), resource);
        repositoryIndices.forEach(index -> index.append(resource));
        return true;
    }

    @Override
    public final boolean remove(R resource) {
        if (!contains(resource)) {
            return false;
        }

        resourcesById.remove(resource.id(), resource);
        repositoryIndices.forEach(index -> index.remove(resource));
        return true;
    }

    private boolean contains(R resource) {
        return contains(resource.id());
    }

    protected void registerIndex(RepositoryIndex<?> repositoryIndex) {
        this.repositoryIndices.add(repositoryIndex);
    }

    protected abstract class RepositoryIndex<K> {

        private final ArrayListMultimap<K, R> resourceByKey;

        public RepositoryIndex() {
            this.resourceByKey = ArrayListMultimap.create();
        }

        final public void append(R resource) {
            K key = extractKey(resource);
            resourceByKey.put(key, resource);
        }

        final public void remove(R resource) {
            K key = extractKey(resource);
            resourceByKey.remove(key, resource);
        }

        final public boolean contains(R resource) {
            K key = extractKey(resource);
            return contains(key);
        }

        final public boolean contains(K key) {
            return resourceByKey.containsKey(key);
        }

        final public R getSingle(K key) {
            List<R> resourceList = resourceByKey.get(key);
            return resourceList.isEmpty() ? null : resourceList.get(0);

        }

        final public Collection<R> get(K key) {
            List<R> resourceList = resourceByKey.get(key);
            return Collections.unmodifiableList(resourceList);
        }

        protected abstract K extractKey(R resource);
    }
}
