package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankTag;
import com.akounts.myapp.service.dto.BankTagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankTag} and its DTO {@link BankTagDTO}.
 */
@Mapper(componentModel = "spring", uses = { BankVendorMapper.class })
public interface BankTagMapper extends EntityMapper<BankTagDTO, BankTag> {
    @Mapping(target = "vendor", source = "vendor", qualifiedByName = "id")
    BankTagDTO toDto(BankTag s);
}
