package dev.peytob.rpg.core.loading.service.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.core.loading.dto.RawTileDto;
import dev.peytob.rpg.core.loading.service.FileStructureService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class JacksonFileTileDataProvider extends JacksonFileRawResourceDataProvider<RawTileDto> {

    private final FileStructureService fileStructureService;

    public JacksonFileTileDataProvider(ObjectMapper objectMapper, FileStructureService fileStructureService) {
        super(objectMapper);
        this.fileStructureService = fileStructureService;
    }

    @Override
    public Collection<RawTileDto> loadResourcesRawData() {
        return loadResourcesData(fileStructureService.getTilesDirectoryPath());
    }
}
