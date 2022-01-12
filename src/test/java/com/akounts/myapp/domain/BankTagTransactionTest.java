package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTagTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTagTransaction.class);
        BankTagTransaction bankTagTransaction1 = new BankTagTransaction();
        bankTagTransaction1.setId(1L);
        BankTagTransaction bankTagTransaction2 = new BankTagTransaction();
        bankTagTransaction2.setId(bankTagTransaction1.getId());
        assertThat(bankTagTransaction1).isEqualTo(bankTagTransaction2);
        bankTagTransaction2.setId(2L);
        assertThat(bankTagTransaction1).isNotEqualTo(bankTagTransaction2);
        bankTagTransaction1.setId(null);
        assertThat(bankTagTransaction1).isNotEqualTo(bankTagTransaction2);
    }
}
