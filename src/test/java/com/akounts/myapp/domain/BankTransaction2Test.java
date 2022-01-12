package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTransaction2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTransaction2.class);
        BankTransaction2 bankTransaction21 = new BankTransaction2();
        bankTransaction21.setId(1L);
        BankTransaction2 bankTransaction22 = new BankTransaction2();
        bankTransaction22.setId(bankTransaction21.getId());
        assertThat(bankTransaction21).isEqualTo(bankTransaction22);
        bankTransaction22.setId(2L);
        assertThat(bankTransaction21).isNotEqualTo(bankTransaction22);
        bankTransaction21.setId(null);
        assertThat(bankTransaction21).isNotEqualTo(bankTransaction22);
    }
}
