package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BridgeRun;
import com.akounts.myapp.service.dto.BridgeRunDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BridgeRun} and its DTO {@link BridgeRunDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BridgeRunMapper extends EntityMapper<BridgeRunDTO, BridgeRun> {}
