package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.Automatch;
import com.akounts.myapp.service.dto.AutomatchDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Automatch} and its DTO {@link AutomatchDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankCategoryMapper.class })
public interface AutomatchMapper extends EntityMapper<AutomatchDTO, Automatch> {
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    AutomatchDTO toDto(Automatch s);
}
