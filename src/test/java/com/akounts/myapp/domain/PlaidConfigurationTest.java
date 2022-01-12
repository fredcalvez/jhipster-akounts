package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidConfiguration.class);
        PlaidConfiguration plaidConfiguration1 = new PlaidConfiguration();
        plaidConfiguration1.setId(1L);
        PlaidConfiguration plaidConfiguration2 = new PlaidConfiguration();
        plaidConfiguration2.setId(plaidConfiguration1.getId());
        assertThat(plaidConfiguration1).isEqualTo(plaidConfiguration2);
        plaidConfiguration2.setId(2L);
        assertThat(plaidConfiguration1).isNotEqualTo(plaidConfiguration2);
        plaidConfiguration1.setId(null);
        assertThat(plaidConfiguration1).isNotEqualTo(plaidConfiguration2);
    }
}
