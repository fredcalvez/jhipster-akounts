package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankCategoryMapperTest {

    private BankCategoryMapper bankCategoryMapper;

    @BeforeEach
    public void setUp() {
        bankCategoryMapper = new BankCategoryMapperImpl();
    }
}
