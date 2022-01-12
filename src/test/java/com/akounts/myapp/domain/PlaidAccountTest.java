package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidAccount.class);
        PlaidAccount plaidAccount1 = new PlaidAccount();
        plaidAccount1.setId(1L);
        PlaidAccount plaidAccount2 = new PlaidAccount();
        plaidAccount2.setId(plaidAccount1.getId());
        assertThat(plaidAccount1).isEqualTo(plaidAccount2);
        plaidAccount2.setId(2L);
        assertThat(plaidAccount1).isNotEqualTo(plaidAccount2);
        plaidAccount1.setId(null);
        assertThat(plaidAccount1).isNotEqualTo(plaidAccount2);
    }
}
