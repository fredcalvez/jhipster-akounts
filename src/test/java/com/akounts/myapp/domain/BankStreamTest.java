package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankStreamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankStream.class);
        BankStream bankStream1 = new BankStream();
        bankStream1.setId(1L);
        BankStream bankStream2 = new BankStream();
        bankStream2.setId(bankStream1.getId());
        assertThat(bankStream1).isEqualTo(bankStream2);
        bankStream2.setId(2L);
        assertThat(bankStream1).isNotEqualTo(bankStream2);
        bankStream1.setId(null);
        assertThat(bankStream1).isNotEqualTo(bankStream2);
    }
}
