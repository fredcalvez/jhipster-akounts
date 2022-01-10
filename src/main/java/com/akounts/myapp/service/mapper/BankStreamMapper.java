package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankStream;
import com.akounts.myapp.service.dto.BankStreamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankStream} and its DTO {@link BankStreamDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankAccountMapper.class })
public interface BankStreamMapper extends EntityMapper<BankStreamDTO, BankStream> {
    @Mapping(target = "mainAccountId", source = "mainAccountId", qualifiedByName = "id")
    BankStreamDTO toDto(BankStream s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankStreamDTO toDtoId(BankStream bankStream);
}
