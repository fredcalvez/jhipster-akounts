package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankVendorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankVendor.class);
        BankVendor bankVendor1 = new BankVendor();
        bankVendor1.setId(1L);
        BankVendor bankVendor2 = new BankVendor();
        bankVendor2.setId(bankVendor1.getId());
        assertThat(bankVendor1).isEqualTo(bankVendor2);
        bankVendor2.setId(2L);
        assertThat(bankVendor1).isNotEqualTo(bankVendor2);
        bankVendor1.setId(null);
        assertThat(bankVendor1).isNotEqualTo(bankVendor2);
    }
}
