package dev.peytob.rpg.server.loader.constants;

import java.io.FileFilter;

public enum DefaultFileFilters {;

    public static final FileFilter JSON_FILE_FILTER = innerFile ->
        innerFile.canRead() && innerFile.getName().endsWith(".json");
}
