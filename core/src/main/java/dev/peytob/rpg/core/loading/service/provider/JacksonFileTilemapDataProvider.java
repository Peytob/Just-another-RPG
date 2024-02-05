package dev.peytob.rpg.core.loading.service.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.core.loading.dto.RawTilemapDto;
import dev.peytob.rpg.core.loading.service.FileStructureService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class JacksonFileTilemapDataProvider extends JacksonFileRawResourceDataProvider<RawTilemapDto> {

    private final FileStructureService fileStructureService;

    public JacksonFileTilemapDataProvider(ObjectMapper objectMapper, FileStructureService fileStructureService) {
        super(objectMapper);
        this.fileStructureService = fileStructureService;
    }

    @Override
    public Collection<RawTilemapDto> loadResourcesRawData() {
        return loadResourcesData(fileStructureService.getTilemapsDirectoryPath());
    }
}
