package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankAccountInterestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccountInterest.class);
        BankAccountInterest bankAccountInterest1 = new BankAccountInterest();
        bankAccountInterest1.setId(1L);
        BankAccountInterest bankAccountInterest2 = new BankAccountInterest();
        bankAccountInterest2.setId(bankAccountInterest1.getId());
        assertThat(bankAccountInterest1).isEqualTo(bankAccountInterest2);
        bankAccountInterest2.setId(2L);
        assertThat(bankAccountInterest1).isNotEqualTo(bankAccountInterest2);
        bankAccountInterest1.setId(null);
        assertThat(bankAccountInterest1).isNotEqualTo(bankAccountInterest2);
    }
}
