package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidTransaction.class);
        PlaidTransaction plaidTransaction1 = new PlaidTransaction();
        plaidTransaction1.setId(1L);
        PlaidTransaction plaidTransaction2 = new PlaidTransaction();
        plaidTransaction2.setId(plaidTransaction1.getId());
        assertThat(plaidTransaction1).isEqualTo(plaidTransaction2);
        plaidTransaction2.setId(2L);
        assertThat(plaidTransaction1).isNotEqualTo(plaidTransaction2);
        plaidTransaction1.setId(null);
        assertThat(plaidTransaction1).isNotEqualTo(plaidTransaction2);
    }
}
