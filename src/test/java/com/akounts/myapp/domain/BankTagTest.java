package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTag.class);
        BankTag bankTag1 = new BankTag();
        bankTag1.setId(1L);
        BankTag bankTag2 = new BankTag();
        bankTag2.setId(bankTag1.getId());
        assertThat(bankTag1).isEqualTo(bankTag2);
        bankTag2.setId(2L);
        assertThat(bankTag1).isNotEqualTo(bankTag2);
        bankTag1.setId(null);
        assertThat(bankTag1).isNotEqualTo(bankTag2);
    }
}
