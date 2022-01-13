package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.FileImport;
import com.akounts.myapp.service.dto.FileImportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileImport} and its DTO {@link FileImportDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileImportMapper extends EntityMapper<FileImportDTO, FileImport> {}
