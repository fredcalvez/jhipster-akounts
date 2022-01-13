package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileImportMapperTest {

    private FileImportMapper fileImportMapper;

    @BeforeEach
    public void setUp() {
        fileImportMapper = new FileImportMapperImpl();
    }
}
