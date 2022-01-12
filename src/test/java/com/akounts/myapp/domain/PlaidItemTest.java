package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidItem.class);
        PlaidItem plaidItem1 = new PlaidItem();
        plaidItem1.setId(1L);
        PlaidItem plaidItem2 = new PlaidItem();
        plaidItem2.setId(plaidItem1.getId());
        assertThat(plaidItem1).isEqualTo(plaidItem2);
        plaidItem2.setId(2L);
        assertThat(plaidItem1).isNotEqualTo(plaidItem2);
        plaidItem1.setId(null);
        assertThat(plaidItem1).isNotEqualTo(plaidItem2);
    }
}
