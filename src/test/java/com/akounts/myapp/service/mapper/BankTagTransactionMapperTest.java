package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTagTransactionMapperTest {

    private BankTagTransactionMapper bankTagTransactionMapper;

    @BeforeEach
    public void setUp() {
        bankTagTransactionMapper = new BankTagTransactionMapperImpl();
    }
}
