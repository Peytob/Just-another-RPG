package dev.peytob.rpg.server.loader.service.provider;

import java.util.Collection;

/**
 * Loads raw resource data. This is only raw data: links may be invalid; id may already exist
 */
public interface RawResourceDataProvider<T> {

    Collection<T> loadResourcesRawData();
}
