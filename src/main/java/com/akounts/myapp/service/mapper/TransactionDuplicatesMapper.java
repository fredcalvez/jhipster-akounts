package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.TransactionDuplicates;
import com.akounts.myapp.service.dto.TransactionDuplicatesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionDuplicates} and its DTO {@link TransactionDuplicatesDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankTransactionMapper.class })
public interface TransactionDuplicatesMapper extends EntityMapper<TransactionDuplicatesDTO, TransactionDuplicates> {
    @Mapping(target = "parentTransactionId", source = "parentTransactionId", qualifiedByName = "id")
    @Mapping(target = "childTransactionId", source = "childTransactionId", qualifiedByName = "id")
    TransactionDuplicatesDTO toDto(TransactionDuplicates s);
}
