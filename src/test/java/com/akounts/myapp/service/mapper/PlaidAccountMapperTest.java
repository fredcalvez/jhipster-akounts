package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaidAccountMapperTest {

    private PlaidAccountMapper plaidAccountMapper;

    @BeforeEach
    public void setUp() {
        plaidAccountMapper = new PlaidAccountMapperImpl();
    }
}