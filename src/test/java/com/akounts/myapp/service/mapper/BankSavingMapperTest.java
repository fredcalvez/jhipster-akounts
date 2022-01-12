package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankSavingMapperTest {

    private BankSavingMapper bankSavingMapper;

    @BeforeEach
    public void setUp() {
        bankSavingMapper = new BankSavingMapperImpl();
    }
}
