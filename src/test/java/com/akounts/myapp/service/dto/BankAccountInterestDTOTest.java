package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankAccountInterestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccountInterestDTO.class);
        BankAccountInterestDTO bankAccountInterestDTO1 = new BankAccountInterestDTO();
        bankAccountInterestDTO1.setId(1L);
        BankAccountInterestDTO bankAccountInterestDTO2 = new BankAccountInterestDTO();
        assertThat(bankAccountInterestDTO1).isNotEqualTo(bankAccountInterestDTO2);
        bankAccountInterestDTO2.setId(bankAccountInterestDTO1.getId());
        assertThat(bankAccountInterestDTO1).isEqualTo(bankAccountInterestDTO2);
        bankAccountInterestDTO2.setId(2L);
        assertThat(bankAccountInterestDTO1).isNotEqualTo(bankAccountInterestDTO2);
        bankAccountInterestDTO1.setId(null);
        assertThat(bankAccountInterestDTO1).isNotEqualTo(bankAccountInterestDTO2);
    }
}
