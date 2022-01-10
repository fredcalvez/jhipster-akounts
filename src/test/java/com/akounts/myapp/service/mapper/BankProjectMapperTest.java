package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankProjectMapperTest {

    private BankProjectMapper bankProjectMapper;

    @BeforeEach
    public void setUp() {
        bankProjectMapper = new BankProjectMapperImpl();
    }
}
