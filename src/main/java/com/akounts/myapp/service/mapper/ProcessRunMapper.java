package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.ProcessRun;
import com.akounts.myapp.service.dto.ProcessRunDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessRun} and its DTO {@link ProcessRunDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessRunMapper extends EntityMapper<ProcessRunDTO, ProcessRun> {}
