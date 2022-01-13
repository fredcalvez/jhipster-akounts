package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaidConfigurationMapperTest {

    private PlaidConfigurationMapper plaidConfigurationMapper;

    @BeforeEach
    public void setUp() {
        plaidConfigurationMapper = new PlaidConfigurationMapperImpl();
    }
}
