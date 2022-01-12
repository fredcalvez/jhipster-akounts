package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.PlaidRun;
import com.akounts.myapp.service.dto.PlaidRunDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlaidRun} and its DTO {@link PlaidRunDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaidRunMapper extends EntityMapper<PlaidRunDTO, PlaidRun> {}
