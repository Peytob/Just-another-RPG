package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.resource.Resource;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Just wraps Map class.
 */
public abstract class BaseRepository<R extends Resource> implements Repository<R> {

    private final Map<Integer, R> resourcesByNumberId = new ConcurrentHashMap<>();

    private final Map<String, R> resourcesByTextId = new ConcurrentHashMap<>();

    private final Collection<RepositoryIndex<?>> repositoryIndices = new CopyOnWriteArrayList<>();

    @Override
    public final R getById(Integer id)  {
        return resourcesByNumberId.get(id);
    }

    @Override
    public final R getById(String textId) {
        return resourcesByTextId.get(textId);
    }

    @Override
    public final Collection<R> getAll() {
        return resourcesByNumberId.values();
    }

    @Override
    public final Integer getCount() {
        return resourcesByTextId.size();
    }

    @Override
    public final boolean contains(Integer id) {
        return resourcesByNumberId.containsKey(id);
    }

    @Override
    public final boolean contains(String id) {
        return resourcesByTextId.containsKey(id);
    }

    @Override
    public final boolean append(R resource) {
        if (contains(resource)) {
            return false;
        }

        resourcesByNumberId.put(resource.id(), resource);
        resourcesByTextId.put(resource.textId(), resource);
        repositoryIndices.forEach(index -> index.append(resource));
        return true;
    }

    @Override
    public final boolean remove(R resource) {
        if (!contains(resource)) {
            return false;
        }

        resourcesByNumberId.remove(resource.id(), resource);
        resourcesByTextId.remove(resource.textId(), resource);
        repositoryIndices.forEach(index -> index.remove(resource));
        return true;
    }

    private boolean contains(R resource) {
        return contains(resource.id()) || contains(resource.textId());
    }

    protected void registerIndex(RepositoryIndex<?> repositoryIndex) {
        this.repositoryIndices.add(repositoryIndex);
    }

    protected abstract class RepositoryIndex<K> {

        // Todo Make guava multimap later
        private final Map<K, R> resourceByKey;

        public RepositoryIndex() {
            this.resourceByKey = new ConcurrentHashMap<>();
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
            return resourceByKey.containsKey(key);
        }

        final public R get(K key) {
            return resourceByKey.get(key);
        }

        protected abstract K extractKey(R resource);
    }
}
