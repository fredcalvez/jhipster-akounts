package com.akounts.myapp.service.mapper;

import com.akounts.myapp.domain.TriggerRun;
import com.akounts.myapp.service.dto.TriggerRunDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TriggerRun} and its DTO {@link TriggerRunDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TriggerRunMapper extends EntityMapper<TriggerRunDTO, TriggerRun> {}
