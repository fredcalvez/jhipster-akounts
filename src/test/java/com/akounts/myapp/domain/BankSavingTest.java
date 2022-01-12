package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankSavingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankSaving.class);
        BankSaving bankSaving1 = new BankSaving();
        bankSaving1.setId(1L);
        BankSaving bankSaving2 = new BankSaving();
        bankSaving2.setId(bankSaving1.getId());
        assertThat(bankSaving1).isEqualTo(bankSaving2);
        bankSaving2.setId(2L);
        assertThat(bankSaving1).isNotEqualTo(bankSaving2);
        bankSaving1.setId(null);
        assertThat(bankSaving1).isNotEqualTo(bankSaving2);
    }
}
