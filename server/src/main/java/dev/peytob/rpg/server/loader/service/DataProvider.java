package dev.peytob.rpg.server.loader.service;

import java.util.Collection;

public interface DataProvider<T> {

    Collection<T> loadData();
}
