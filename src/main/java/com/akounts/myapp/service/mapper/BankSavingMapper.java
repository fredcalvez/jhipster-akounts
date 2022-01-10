package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankSaving;
import com.akounts.myapp.service.dto.BankSavingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankSaving} and its DTO {@link BankSavingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankSavingMapper extends EntityMapper<BankSavingDTO, BankSaving> {}
