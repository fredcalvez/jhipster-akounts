package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.FileConfig;
import com.akounts.myapp.service.dto.FileConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileConfig} and its DTO {@link FileConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileConfigMapper extends EntityMapper<FileConfigDTO, FileConfig> {}
