package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BridgeUser;
import com.akounts.myapp.service.dto.BridgeUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BridgeUser} and its DTO {@link BridgeUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BridgeUserMapper extends EntityMapper<BridgeUserDTO, BridgeUser> {}
