package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankAccountInterestMapperTest {

    private BankAccountInterestMapper bankAccountInterestMapper;

    @BeforeEach
    public void setUp() {
        bankAccountInterestMapper = new BankAccountInterestMapperImpl();
    }
}
