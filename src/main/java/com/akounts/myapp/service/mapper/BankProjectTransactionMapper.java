package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankProjectTransaction;
import com.akounts.myapp.service.dto.BankProjectTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankProjectTransaction} and its DTO {@link BankProjectTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankTransactionMapper.class, BankProjectMapper.class })
public interface BankProjectTransactionMapper extends EntityMapper<BankProjectTransactionDTO, BankProjectTransaction> {
    @Mapping(target = "transactionId", source = "transactionId", qualifiedByName = "id")
    @Mapping(target = "projectId", source = "projectId", qualifiedByName = "id")
    BankProjectTransactionDTO toDto(BankProjectTransaction s);
}
