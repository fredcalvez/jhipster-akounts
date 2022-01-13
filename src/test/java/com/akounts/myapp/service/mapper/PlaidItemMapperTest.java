package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaidItemMapperTest {

    private PlaidItemMapper plaidItemMapper;

    @BeforeEach
    public void setUp() {
        plaidItemMapper = new PlaidItemMapperImpl();
    }
}
