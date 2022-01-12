package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankProjectTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankProjectTransactionDTO.class);
        BankProjectTransactionDTO bankProjectTransactionDTO1 = new BankProjectTransactionDTO();
        bankProjectTransactionDTO1.setId(1L);
        BankProjectTransactionDTO bankProjectTransactionDTO2 = new BankProjectTransactionDTO();
        assertThat(bankProjectTransactionDTO1).isNotEqualTo(bankProjectTransactionDTO2);
        bankProjectTransactionDTO2.setId(bankProjectTransactionDTO1.getId());
        assertThat(bankProjectTransactionDTO1).isEqualTo(bankProjectTransactionDTO2);
        bankProjectTransactionDTO2.setId(2L);
        assertThat(bankProjectTransactionDTO1).isNotEqualTo(bankProjectTransactionDTO2);
        bankProjectTransactionDTO1.setId(null);
        assertThat(bankProjectTransactionDTO1).isNotEqualTo(bankProjectTransactionDTO2);
    }
}
