package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankInstitution;
import com.akounts.myapp.service.dto.BankInstitutionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankInstitution} and its DTO {@link BankInstitutionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankInstitutionMapper extends EntityMapper<BankInstitutionDTO, BankInstitution> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankInstitutionDTO toDtoId(BankInstitution bankInstitution);
}
