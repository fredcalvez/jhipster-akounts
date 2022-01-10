package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AkountsSettingsMapperTest {

    private AkountsSettingsMapper akountsSettingsMapper;

    @BeforeEach
    public void setUp() {
        akountsSettingsMapper = new AkountsSettingsMapperImpl();
    }
}
