package dev.peytob.rpg.server.loader.service.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.core.loading.service.provider.JacksonFileRawResourceDataProvider;
import dev.peytob.rpg.server.loader.dto.RawWorldContextDto;
import dev.peytob.rpg.core.loading.service.FileStructureService;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JacksonFileWorldContextDataProvider extends JacksonFileRawResourceDataProvider<RawWorldContextDto> {

    private final FileStructureService fileStructureService;

    public JacksonFileWorldContextDataProvider(ObjectMapper objectMapper, FileStructureService fileStructureService) {
        super(objectMapper);
        this.fileStructureService = fileStructureService;
    }

    @Override
    public Collection<RawWorldContextDto> loadResourcesRawData() {
        return loadResourcesData(fileStructureService.getWorldContextsPath());
    }
}
