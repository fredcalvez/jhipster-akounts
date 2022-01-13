package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidRunTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidRun.class);
        PlaidRun plaidRun1 = new PlaidRun();
        plaidRun1.setId(1L);
        PlaidRun plaidRun2 = new PlaidRun();
        plaidRun2.setId(plaidRun1.getId());
        assertThat(plaidRun1).isEqualTo(plaidRun2);
        plaidRun2.setId(2L);
        assertThat(plaidRun1).isNotEqualTo(plaidRun2);
        plaidRun1.setId(null);
        assertThat(plaidRun1).isNotEqualTo(plaidRun2);
    }
}
