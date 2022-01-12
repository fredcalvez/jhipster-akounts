package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.Bridge;
import com.akounts.myapp.service.dto.BridgeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bridge} and its DTO {@link BridgeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BridgeMapper extends EntityMapper<BridgeDTO, Bridge> {}
