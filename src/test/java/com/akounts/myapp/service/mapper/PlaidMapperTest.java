package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaidMapperTest {

    private PlaidMapper plaidMapper;

    @BeforeEach
    public void setUp() {
        plaidMapper = new PlaidMapperImpl();
    }
}
