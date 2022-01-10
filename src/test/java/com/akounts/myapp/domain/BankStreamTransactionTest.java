package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankStreamTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankStreamTransaction.class);
        BankStreamTransaction bankStreamTransaction1 = new BankStreamTransaction();
        bankStreamTransaction1.setId(1L);
        BankStreamTransaction bankStreamTransaction2 = new BankStreamTransaction();
        bankStreamTransaction2.setId(bankStreamTransaction1.getId());
        assertThat(bankStreamTransaction1).isEqualTo(bankStreamTransaction2);
        bankStreamTransaction2.setId(2L);
        assertThat(bankStreamTransaction1).isNotEqualTo(bankStreamTransaction2);
        bankStreamTransaction1.setId(null);
        assertThat(bankStreamTransaction1).isNotEqualTo(bankStreamTransaction2);
    }
}
