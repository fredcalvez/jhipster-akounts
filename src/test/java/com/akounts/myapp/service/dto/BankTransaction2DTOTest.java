package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTransaction2DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTransaction2DTO.class);
        BankTransaction2DTO bankTransaction2DTO1 = new BankTransaction2DTO();
        bankTransaction2DTO1.setId(1L);
        BankTransaction2DTO bankTransaction2DTO2 = new BankTransaction2DTO();
        assertThat(bankTransaction2DTO1).isNotEqualTo(bankTransaction2DTO2);
        bankTransaction2DTO2.setId(bankTransaction2DTO1.getId());
        assertThat(bankTransaction2DTO1).isEqualTo(bankTransaction2DTO2);
        bankTransaction2DTO2.setId(2L);
        assertThat(bankTransaction2DTO1).isNotEqualTo(bankTransaction2DTO2);
        bankTransaction2DTO1.setId(null);
        assertThat(bankTransaction2DTO1).isNotEqualTo(bankTransaction2DTO2);
    }
}
