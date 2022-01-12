package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTagTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTagTransactionDTO.class);
        BankTagTransactionDTO bankTagTransactionDTO1 = new BankTagTransactionDTO();
        bankTagTransactionDTO1.setId(1L);
        BankTagTransactionDTO bankTagTransactionDTO2 = new BankTagTransactionDTO();
        assertThat(bankTagTransactionDTO1).isNotEqualTo(bankTagTransactionDTO2);
        bankTagTransactionDTO2.setId(bankTagTransactionDTO1.getId());
        assertThat(bankTagTransactionDTO1).isEqualTo(bankTagTransactionDTO2);
        bankTagTransactionDTO2.setId(2L);
        assertThat(bankTagTransactionDTO1).isNotEqualTo(bankTagTransactionDTO2);
        bankTagTransactionDTO1.setId(null);
        assertThat(bankTagTransactionDTO1).isNotEqualTo(bankTagTransactionDTO2);
    }
}
