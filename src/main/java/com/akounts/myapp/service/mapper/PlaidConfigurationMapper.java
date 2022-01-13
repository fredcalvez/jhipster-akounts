package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.PlaidConfiguration;
import com.akounts.myapp.service.dto.PlaidConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlaidConfiguration} and its DTO {@link PlaidConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaidConfigurationMapper extends EntityMapper<PlaidConfigurationDTO, PlaidConfiguration> {}
