package com.akounts.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankInstitutionMapperTest {

    private BankInstitutionMapper bankInstitutionMapper;

    @BeforeEach
    public void setUp() {
        bankInstitutionMapper = new BankInstitutionMapperImpl();
    }
}
