package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionDuplicatesMapperTest {

    private TransactionDuplicatesMapper transactionDuplicatesMapper;

    @BeforeEach
    public void setUp() {
        transactionDuplicatesMapper = new TransactionDuplicatesMapperImpl();
    }
}
