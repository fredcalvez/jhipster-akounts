package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankStreamTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankStreamTransactionDTO.class);
        BankStreamTransactionDTO bankStreamTransactionDTO1 = new BankStreamTransactionDTO();
        bankStreamTransactionDTO1.setId(1L);
        BankStreamTransactionDTO bankStreamTransactionDTO2 = new BankStreamTransactionDTO();
        assertThat(bankStreamTransactionDTO1).isNotEqualTo(bankStreamTransactionDTO2);
        bankStreamTransactionDTO2.setId(bankStreamTransactionDTO1.getId());
        assertThat(bankStreamTransactionDTO1).isEqualTo(bankStreamTransactionDTO2);
        bankStreamTransactionDTO2.setId(2L);
        assertThat(bankStreamTransactionDTO1).isNotEqualTo(bankStreamTransactionDTO2);
        bankStreamTransactionDTO1.setId(null);
        assertThat(bankStreamTransactionDTO1).isNotEqualTo(bankStreamTransactionDTO2);
    }
}
