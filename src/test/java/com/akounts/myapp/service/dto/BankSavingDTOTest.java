package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankSavingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankSavingDTO.class);
        BankSavingDTO bankSavingDTO1 = new BankSavingDTO();
        bankSavingDTO1.setId(1L);
        BankSavingDTO bankSavingDTO2 = new BankSavingDTO();
        assertThat(bankSavingDTO1).isNotEqualTo(bankSavingDTO2);
        bankSavingDTO2.setId(bankSavingDTO1.getId());
        assertThat(bankSavingDTO1).isEqualTo(bankSavingDTO2);
        bankSavingDTO2.setId(2L);
        assertThat(bankSavingDTO1).isNotEqualTo(bankSavingDTO2);
        bankSavingDTO1.setId(null);
        assertThat(bankSavingDTO1).isNotEqualTo(bankSavingDTO2);
    }
}
