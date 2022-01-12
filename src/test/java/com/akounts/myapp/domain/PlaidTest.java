package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plaid.class);
        Plaid plaid1 = new Plaid();
        plaid1.setId(1L);
        Plaid plaid2 = new Plaid();
        plaid2.setId(plaid1.getId());
        assertThat(plaid1).isEqualTo(plaid2);
        plaid2.setId(2L);
        assertThat(plaid1).isNotEqualTo(plaid2);
        plaid1.setId(null);
        assertThat(plaid1).isNotEqualTo(plaid2);
    }
}
