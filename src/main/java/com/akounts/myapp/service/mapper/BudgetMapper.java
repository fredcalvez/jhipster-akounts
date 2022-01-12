package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.Budget;
import com.akounts.myapp.service.dto.BudgetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Budget} and its DTO {@link BudgetDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankCategoryMapper.class })
public interface BudgetMapper extends EntityMapper<BudgetDTO, Budget> {
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    BudgetDTO toDto(Budget s);
}
