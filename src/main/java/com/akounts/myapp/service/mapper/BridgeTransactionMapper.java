package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BridgeTransaction;
import com.akounts.myapp.service.dto.BridgeTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BridgeTransaction} and its DTO {@link BridgeTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BridgeTransactionMapper extends EntityMapper<BridgeTransactionDTO, BridgeTransaction> {}
