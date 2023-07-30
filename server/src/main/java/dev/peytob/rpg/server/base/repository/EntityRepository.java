package dev.peytob.rpg.server.base.repository;

import dev.peytob.rpg.engine.repositry.BaseRepository;
import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.server.base.resource.entity.Entity;

public abstract class EntityRepository<E extends Entity & Resource> extends BaseRepository<E> {
}
