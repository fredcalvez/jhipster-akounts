package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaidTransactionMapperTest {

    private PlaidTransactionMapper plaidTransactionMapper;

    @BeforeEach
    public void setUp() {
        plaidTransactionMapper = new PlaidTransactionMapperImpl();
    }
}
