package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTransactionAutomatchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTransactionAutomatchDTO.class);
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO1 = new BankTransactionAutomatchDTO();
        bankTransactionAutomatchDTO1.setId(1L);
        BankTransactionAutomatchDTO bankTransactionAutomatchDTO2 = new BankTransactionAutomatchDTO();
        assertThat(bankTransactionAutomatchDTO1).isNotEqualTo(bankTransactionAutomatchDTO2);
        bankTransactionAutomatchDTO2.setId(bankTransactionAutomatchDTO1.getId());
        assertThat(bankTransactionAutomatchDTO1).isEqualTo(bankTransactionAutomatchDTO2);
        bankTransactionAutomatchDTO2.setId(2L);
        assertThat(bankTransactionAutomatchDTO1).isNotEqualTo(bankTransactionAutomatchDTO2);
        bankTransactionAutomatchDTO1.setId(null);
        assertThat(bankTransactionAutomatchDTO1).isNotEqualTo(bankTransactionAutomatchDTO2);
    }
}
