package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.resource.Resource;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Just wraps Map class.
 */
public class BaseRepository<R extends Record & Resource> implements Repository<R> {

    private final Map<Integer, R> resourcesByNumberId;

    private final Map<String, R> resourcesByTextId;

    BaseRepository() {
        this.resourcesByNumberId = new ConcurrentHashMap<>();
        this.resourcesByTextId = new ConcurrentHashMap<>();
    }

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
        if (isResourcePresented(resource)) {
            return false;
        }

        resourcesByNumberId.put(resource.id(), resource);
        resourcesByTextId.put(resource.textId(), resource);
        return true;
    }

    @Override
    public final boolean remove(R resource) {
        if (!isResourcePresented(resource)) {
            return false;
        }

        resourcesByNumberId.remove(resource.id(), resource);
        resourcesByTextId.remove(resource.textId(), resource);
        return true;
    }

    private boolean isResourcePresented(R resource) {
        return contains(resource.id()) || contains(resource.textId());
    }
}
