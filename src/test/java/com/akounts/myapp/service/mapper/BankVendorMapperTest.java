package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankVendorMapperTest {

    private BankVendorMapper bankVendorMapper;

    @BeforeEach
    public void setUp() {
        bankVendorMapper = new BankVendorMapperImpl();
    }
}
