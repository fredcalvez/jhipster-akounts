package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankProjectTransactionMapperTest {

    private BankProjectTransactionMapper bankProjectTransactionMapper;

    @BeforeEach
    public void setUp() {
        bankProjectTransactionMapper = new BankProjectTransactionMapperImpl();
    }
}
