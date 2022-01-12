package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankVendorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankVendorDTO.class);
        BankVendorDTO bankVendorDTO1 = new BankVendorDTO();
        bankVendorDTO1.setId(1L);
        BankVendorDTO bankVendorDTO2 = new BankVendorDTO();
        assertThat(bankVendorDTO1).isNotEqualTo(bankVendorDTO2);
        bankVendorDTO2.setId(bankVendorDTO1.getId());
        assertThat(bankVendorDTO1).isEqualTo(bankVendorDTO2);
        bankVendorDTO2.setId(2L);
        assertThat(bankVendorDTO1).isNotEqualTo(bankVendorDTO2);
        bankVendorDTO1.setId(null);
        assertThat(bankVendorDTO1).isNotEqualTo(bankVendorDTO2);
    }
}
