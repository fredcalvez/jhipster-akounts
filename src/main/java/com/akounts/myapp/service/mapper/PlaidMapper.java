package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.Plaid;
import com.akounts.myapp.service.dto.PlaidDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plaid} and its DTO {@link PlaidDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaidMapper extends EntityMapper<PlaidDTO, Plaid> {}
