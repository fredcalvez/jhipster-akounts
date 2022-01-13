package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTagMapperTest {

    private BankTagMapper bankTagMapper;

    @BeforeEach
    public void setUp() {
        bankTagMapper = new BankTagMapperImpl();
    }
}
