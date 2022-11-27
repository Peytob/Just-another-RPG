package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.resource.Resource;

import java.util.Collection;

public interface Repository<R extends Record & Resource> {
    R getById(Integer id);

    R getById(String textId);

    Collection<R> getAll();

    Integer getCount();

    boolean contains(Integer id);

    boolean contains(String id);

    boolean append(R resource);

    boolean remove(R resource);
}
