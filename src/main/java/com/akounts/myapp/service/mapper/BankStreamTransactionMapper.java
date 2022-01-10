package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankStreamTransaction;
import com.akounts.myapp.service.dto.BankStreamTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankStreamTransaction} and its DTO {@link BankStreamTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankTransactionMapper.class, BankStreamMapper.class })
public interface BankStreamTransactionMapper extends EntityMapper<BankStreamTransactionDTO, BankStreamTransaction> {
    @Mapping(target = "transactionId", source = "transactionId", qualifiedByName = "id")
    @Mapping(target = "streamId", source = "streamId", qualifiedByName = "id")
    BankStreamTransactionDTO toDto(BankStreamTransaction s);
}
