package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.RebaseHistory;
import com.akounts.myapp.service.dto.RebaseHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RebaseHistory} and its DTO {@link RebaseHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankTransactionMapper.class })
public interface RebaseHistoryMapper extends EntityMapper<RebaseHistoryDTO, RebaseHistory> {
    @Mapping(target = "transactionId", source = "transactionId", qualifiedByName = "id")
    RebaseHistoryDTO toDto(RebaseHistory s);
}
