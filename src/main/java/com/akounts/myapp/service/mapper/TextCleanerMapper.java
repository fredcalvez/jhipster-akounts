package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.TextCleaner;
import com.akounts.myapp.service.dto.TextCleanerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TextCleaner} and its DTO {@link TextCleanerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TextCleanerMapper extends EntityMapper<TextCleanerDTO, TextCleaner> {}
