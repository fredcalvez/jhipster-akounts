package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaidRunMapperTest {

    private PlaidRunMapper plaidRunMapper;

    @BeforeEach
    public void setUp() {
        plaidRunMapper = new PlaidRunMapperImpl();
    }
}
