package dev.peytob.rpg.server.loader.service;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static java.util.Objects.requireNonNullElse;

/**
 * Base service, that loads data from given file or directory.
 * Can be split into two classes - DataParser (can parse Reader data) and DataReader (can build {@link Reader} from
 * some source). For this class: FileDataReader and JsonDataParser. But this action useless now...
 * @param <T> Type, that should be loaded from files.
 */
@Slf4j
public abstract class FileDataLoader<T> {

    private static final File[] EMPTY_DIRECTORY_FILES = new File[0];

    protected abstract T parseFile(Reader fileReader) throws Exception;

    protected abstract FileFilter getDirectoryFileFilter(File file);

    /**
     * Loads data from given file or directory.
     * @throws IllegalArgumentException If file not exists, cant be read or parsed
     */
    public Collection<T> loadData(Path path) {
        log.info("Trying to load data from {}. Loader: {}", path, getClass().getName());

        File baseFile = path.toFile();
        String absolutePath = baseFile.getAbsolutePath();

        log.info("Resolved path {} as absolute path {}", path, absolutePath);

        if (!baseFile.exists() || baseFile.isHidden()) {
            log.error("File {} not exists", absolutePath);
            throw new IllegalArgumentException("File is not exists");
        }

        if (!baseFile.canRead()) {
            log.error("File {} cannot be read", absolutePath);
            throw new IllegalArgumentException("File cannot be read");
        }

        if (baseFile.isFile()) {
            return Collections.singleton(loadFile(baseFile));
        } else if (baseFile.isDirectory()) {
            return loadDirectoryFiles(baseFile);
        } else {
            log.error("File {} cannot be interpreted as file or directory", absolutePath);
            throw new IllegalArgumentException("File cannot be interpreted as file or directory");
        }
    }

    private Collection<T> loadDirectoryFiles(File directory) {
        FileFilter fileFilter = getDirectoryFileFilter(directory);
        File[] innerFiles = requireNonNullElse(directory.listFiles(fileFilter), EMPTY_DIRECTORY_FILES);
        return Arrays.stream(innerFiles).map(this::loadFile).toList();
    }

    private T loadFile(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            T parsedData = parseFile(fileReader);
            return Objects.requireNonNull(parsedData, "Parsed data should be not null");
        } catch (IOException e) {
            log.error("Exception while reading file {}", file.getAbsolutePath(), e);
            throw new IllegalArgumentException("File cannot be read", e);
        } catch (Exception e) {
            log.error("Unresolved exception while reading or parsing file {}", file.getAbsolutePath());
            throw new IllegalArgumentException("Unresolved exception while reading or parsing file", e);
        }
    }
}
