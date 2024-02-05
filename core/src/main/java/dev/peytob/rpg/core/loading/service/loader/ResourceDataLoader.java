package dev.peytob.rpg.core.loading.service.loader;

import java.util.Collection;

/**
 * Loads raw resources from data sources and convert it to game resource format.
 */
public interface ResourceDataLoader<T> {

    Collection<T> loadResources();
}
