package dev.peytob.rpg.core.loading.service.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.core.loading.dto.RawWorldDto;
import dev.peytob.rpg.core.loading.service.FileStructureService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class JacksonFileWorldDataProvider extends JacksonFileRawResourceDataProvider<RawWorldDto> {

    private final FileStructureService fileStructureService;

    public JacksonFileWorldDataProvider(ObjectMapper objectMapper, FileStructureService fileStructureService) {
        super(objectMapper);
        this.fileStructureService = fileStructureService;
    }

    @Override
    public Collection<RawWorldDto> loadResourcesRawData() {
        return loadResourcesData(fileStructureService.getWorldsDirectoryPath());
    }
}
