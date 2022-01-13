package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankStreamTransactionMapperTest {

    private BankStreamTransactionMapper bankStreamTransactionMapper;

    @BeforeEach
    public void setUp() {
        bankStreamTransactionMapper = new BankStreamTransactionMapperImpl();
    }
}
