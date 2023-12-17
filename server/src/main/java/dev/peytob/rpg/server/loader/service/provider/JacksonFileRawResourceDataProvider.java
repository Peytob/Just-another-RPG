package dev.peytob.rpg.server.loader.service.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static java.util.Objects.requireNonNullElse;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract class JacksonFileRawResourceDataProvider<T> implements RawResourceDataProvider<T> {

    private static final FileFilter JSON_FILE_FILTER = innerFile ->
        innerFile.canRead() && innerFile.getName().endsWith(".json");

    private static final File[] EMPTY_DIRECTORY_FILES = new File[0];

    private final ObjectMapper objectMapper;

    protected Collection<T> loadResourcesData(Path path) {
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
        File[] innerFiles = requireNonNullElse(directory.listFiles(JSON_FILE_FILTER), EMPTY_DIRECTORY_FILES);
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

    @SuppressWarnings("unchecked")
    private T parseFile(Reader reader) throws IOException {
        Class<T> type = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), RawResourceDataProvider.class);
        return objectMapper.readValue(reader, type);
    }
}
