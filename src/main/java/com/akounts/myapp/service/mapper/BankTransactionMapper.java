package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankTransaction;
import com.akounts.myapp.service.dto.BankTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankTransaction} and its DTO {@link BankTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankCategoryMapper.class, BankAccountMapper.class })
public interface BankTransactionMapper extends EntityMapper<BankTransactionDTO, BankTransaction> {
    @Mapping(target = "catId", source = "catId", qualifiedByName = "id")
    @Mapping(target = "accountId", source = "accountId", qualifiedByName = "id")
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    @Mapping(target = "account", source = "account", qualifiedByName = "id")
    BankTransactionDTO toDto(BankTransaction s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankTransactionDTO toDtoId(BankTransaction bankTransaction);
}
