package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.resource.Resource;

import java.util.Collection;
import java.util.Optional;

public interface Repository<R extends Resource> {

    Optional<R> getById(String textId);

    Collection<R> getAll();

    Integer getCount();

    boolean contains(String id);

    boolean append(R resource);

    boolean remove(R resource);
}
