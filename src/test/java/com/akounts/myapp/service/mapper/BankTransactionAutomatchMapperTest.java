package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTransactionAutomatchMapperTest {

    private BankTransactionAutomatchMapper bankTransactionAutomatchMapper;

    @BeforeEach
    public void setUp() {
        bankTransactionAutomatchMapper = new BankTransactionAutomatchMapperImpl();
    }
}
