package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.PlaidAccount;
import com.akounts.myapp.service.dto.PlaidAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlaidAccount} and its DTO {@link PlaidAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaidAccountMapper extends EntityMapper<PlaidAccountDTO, PlaidAccount> {}
