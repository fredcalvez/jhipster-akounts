package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankTransactionAutomatch;
import com.akounts.myapp.service.dto.BankTransactionAutomatchDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankTransactionAutomatch} and its DTO {@link BankTransactionAutomatchDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankTransactionMapper.class, AutomatchMapper.class })
public interface BankTransactionAutomatchMapper extends EntityMapper<BankTransactionAutomatchDTO, BankTransactionAutomatch> {
    @Mapping(target = "transactionId", source = "transactionId", qualifiedByName = "id")
    @Mapping(target = "automatchId", source = "automatchId", qualifiedByName = "id")
    BankTransactionAutomatchDTO toDto(BankTransactionAutomatch s);
}
