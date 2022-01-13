package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankAccountInterest;
import com.akounts.myapp.service.dto.BankAccountInterestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankAccountInterest} and its DTO {@link BankAccountInterestDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankAccountMapper.class })
public interface BankAccountInterestMapper extends EntityMapper<BankAccountInterestDTO, BankAccountInterest> {
    @Mapping(target = "creditedAccountId", source = "creditedAccountId", qualifiedByName = "id")
    BankAccountInterestDTO toDto(BankAccountInterest s);
}
