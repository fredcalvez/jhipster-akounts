package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankProject;
import com.akounts.myapp.service.dto.BankProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankProject} and its DTO {@link BankProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankAccountMapper.class })
public interface BankProjectMapper extends EntityMapper<BankProjectDTO, BankProject> {
    @Mapping(target = "mainAccountId", source = "mainAccountId", qualifiedByName = "id")
    BankProjectDTO toDto(BankProject s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankProjectDTO toDtoId(BankProject bankProject);
}
