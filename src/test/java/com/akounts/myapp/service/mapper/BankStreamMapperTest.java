package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankStreamMapperTest {

    private BankStreamMapper bankStreamMapper;

    @BeforeEach
    public void setUp() {
        bankStreamMapper = new BankStreamMapperImpl();
    }
}
