package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.PlaidTransaction;
import com.akounts.myapp.service.dto.PlaidTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlaidTransaction} and its DTO {@link PlaidTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaidTransactionMapper extends EntityMapper<PlaidTransactionDTO, PlaidTransaction> {}
