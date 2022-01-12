package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTransactionAutomatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTransactionAutomatch.class);
        BankTransactionAutomatch bankTransactionAutomatch1 = new BankTransactionAutomatch();
        bankTransactionAutomatch1.setId(1L);
        BankTransactionAutomatch bankTransactionAutomatch2 = new BankTransactionAutomatch();
        bankTransactionAutomatch2.setId(bankTransactionAutomatch1.getId());
        assertThat(bankTransactionAutomatch1).isEqualTo(bankTransactionAutomatch2);
        bankTransactionAutomatch2.setId(2L);
        assertThat(bankTransactionAutomatch1).isNotEqualTo(bankTransactionAutomatch2);
        bankTransactionAutomatch1.setId(null);
        assertThat(bankTransactionAutomatch1).isNotEqualTo(bankTransactionAutomatch2);
    }
}
