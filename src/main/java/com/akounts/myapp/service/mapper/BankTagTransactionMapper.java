package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankTagTransaction;
import com.akounts.myapp.service.dto.BankTagTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankTagTransaction} and its DTO {@link BankTagTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankTagTransactionMapper extends EntityMapper<BankTagTransactionDTO, BankTagTransaction> {}
