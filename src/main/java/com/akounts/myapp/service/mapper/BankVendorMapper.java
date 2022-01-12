package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankVendor;
import com.akounts.myapp.service.dto.BankVendorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankVendor} and its DTO {@link BankVendorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankVendorMapper extends EntityMapper<BankVendorDTO, BankVendor> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankVendorDTO toDtoId(BankVendor bankVendor);
}
