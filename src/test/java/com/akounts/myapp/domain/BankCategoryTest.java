package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankCategory.class);
        BankCategory bankCategory1 = new BankCategory();
        bankCategory1.setId(1L);
        BankCategory bankCategory2 = new BankCategory();
        bankCategory2.setId(bankCategory1.getId());
        assertThat(bankCategory1).isEqualTo(bankCategory2);
        bankCategory2.setId(2L);
        assertThat(bankCategory1).isNotEqualTo(bankCategory2);
        bankCategory1.setId(null);
        assertThat(bankCategory1).isNotEqualTo(bankCategory2);
    }
}
