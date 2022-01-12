package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTransactionMapperTest {

    private BankTransactionMapper bankTransactionMapper;

    @BeforeEach
    public void setUp() {
        bankTransactionMapper = new BankTransactionMapperImpl();
    }
}
