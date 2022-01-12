package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BridgeAccount;
import com.akounts.myapp.service.dto.BridgeAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BridgeAccount} and its DTO {@link BridgeAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BridgeAccountMapper extends EntityMapper<BridgeAccountDTO, BridgeAccount> {}
