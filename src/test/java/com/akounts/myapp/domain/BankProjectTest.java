package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankProject.class);
        BankProject bankProject1 = new BankProject();
        bankProject1.setId(1L);
        BankProject bankProject2 = new BankProject();
        bankProject2.setId(bankProject1.getId());
        assertThat(bankProject1).isEqualTo(bankProject2);
        bankProject2.setId(2L);
        assertThat(bankProject1).isNotEqualTo(bankProject2);
        bankProject1.setId(null);
        assertThat(bankProject1).isNotEqualTo(bankProject2);
    }
}
