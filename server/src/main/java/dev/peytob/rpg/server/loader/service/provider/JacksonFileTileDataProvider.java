package dev.peytob.rpg.server.loader.service.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.server.loader.service.FileStructureService;
import dev.peytob.rpg.server.loader.service.dto.RawTileDto;
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
