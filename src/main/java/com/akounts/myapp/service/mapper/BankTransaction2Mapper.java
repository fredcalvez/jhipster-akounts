package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.BankTransaction2;
import com.akounts.myapp.service.dto.BankTransaction2DTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankTransaction2} and its DTO {@link BankTransaction2DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankTransaction2Mapper extends EntityMapper<BankTransaction2DTO, BankTransaction2> {}
