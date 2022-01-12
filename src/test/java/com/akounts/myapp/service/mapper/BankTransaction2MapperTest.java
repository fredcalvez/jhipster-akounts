package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTransaction2MapperTest {

    private BankTransaction2Mapper bankTransaction2Mapper;

    @BeforeEach
    public void setUp() {
        bankTransaction2Mapper = new BankTransaction2MapperImpl();
    }
}
