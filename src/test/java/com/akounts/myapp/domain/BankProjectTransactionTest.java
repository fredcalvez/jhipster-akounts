package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankProjectTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankProjectTransaction.class);
        BankProjectTransaction bankProjectTransaction1 = new BankProjectTransaction();
        bankProjectTransaction1.setId(1L);
        BankProjectTransaction bankProjectTransaction2 = new BankProjectTransaction();
        bankProjectTransaction2.setId(bankProjectTransaction1.getId());
        assertThat(bankProjectTransaction1).isEqualTo(bankProjectTransaction2);
        bankProjectTransaction2.setId(2L);
        assertThat(bankProjectTransaction1).isNotEqualTo(bankProjectTransaction2);
        bankProjectTransaction1.setId(null);
        assertThat(bankProjectTransaction1).isNotEqualTo(bankProjectTransaction2);
    }
}
