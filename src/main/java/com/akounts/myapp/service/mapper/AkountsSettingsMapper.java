package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.AkountsSettings;
import com.akounts.myapp.service.dto.AkountsSettingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AkountsSettings} and its DTO {@link AkountsSettingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AkountsSettingsMapper extends EntityMapper<AkountsSettingsDTO, AkountsSettings> {}
