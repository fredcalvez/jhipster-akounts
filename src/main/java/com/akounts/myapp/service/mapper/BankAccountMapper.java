package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankAccount;
import com.akounts.myapp.service.dto.BankAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankAccount} and its DTO {@link BankAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankInstitutionMapper.class })
public interface BankAccountMapper extends EntityMapper<BankAccountDTO, BankAccount> {
    @Mapping(target = "institution", source = "institution", qualifiedByName = "id")
    BankAccountDTO toDto(BankAccount s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankAccountDTO toDtoId(BankAccount bankAccount);
}
