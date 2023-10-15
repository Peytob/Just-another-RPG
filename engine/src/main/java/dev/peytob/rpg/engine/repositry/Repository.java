package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.resource.Resource;

import java.util.Collection;

public interface Repository<R extends Resource> {

    R getById(String textId);

    Collection<R> getAll();

    Integer getCount();

    boolean contains(String id);

    boolean append(R resource);

    boolean remove(R resource);
}
