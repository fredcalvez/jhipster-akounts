package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.PlaidItem;
import com.akounts.myapp.service.dto.PlaidItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlaidItem} and its DTO {@link PlaidItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaidItemMapper extends EntityMapper<PlaidItemDTO, PlaidItem> {}
