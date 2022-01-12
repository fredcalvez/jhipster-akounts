package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutomatchMapperTest {

    private AutomatchMapper automatchMapper;

    @BeforeEach
    public void setUp() {
        automatchMapper = new AutomatchMapperImpl();
    }
}
