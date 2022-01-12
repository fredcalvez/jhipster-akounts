package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankCategory;
import com.akounts.myapp.service.dto.BankCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankCategory} and its DTO {@link BankCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankCategoryMapper extends EntityMapper<BankCategoryDTO, BankCategory> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankCategoryDTO toDtoId(BankCategory bankCategory);
}
