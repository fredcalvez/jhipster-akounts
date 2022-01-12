package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextCleanerMapperTest {

    private TextCleanerMapper textCleanerMapper;

    @BeforeEach
    public void setUp() {
        textCleanerMapper = new TextCleanerMapperImpl();
    }
}
