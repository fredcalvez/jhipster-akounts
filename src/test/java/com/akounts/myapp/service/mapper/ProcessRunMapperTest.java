package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessRunMapperTest {

    private ProcessRunMapper processRunMapper;

    @BeforeEach
    public void setUp() {
        processRunMapper = new ProcessRunMapperImpl();
    }
}
