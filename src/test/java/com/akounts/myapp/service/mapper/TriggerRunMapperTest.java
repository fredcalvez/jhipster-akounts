package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TriggerRunMapperTest {

    private TriggerRunMapper triggerRunMapper;

    @BeforeEach
    public void setUp() {
        triggerRunMapper = new TriggerRunMapperImpl();
    }
}
