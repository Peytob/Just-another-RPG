package dev.peytob.rpg.ecs.exception;

import dev.peytob.rpg.ecs.entity.filer.Filter;

public class FilterAlreadyRegisteredException extends RuntimeException {

    private final Filter filter;

    public FilterAlreadyRegisteredException(Filter filter) {
        super("Filter already registered!");
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }
}
