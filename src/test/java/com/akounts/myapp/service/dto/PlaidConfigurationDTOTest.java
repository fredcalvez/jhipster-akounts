package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidConfigurationDTO.class);
        PlaidConfigurationDTO plaidConfigurationDTO1 = new PlaidConfigurationDTO();
        plaidConfigurationDTO1.setId(1L);
        PlaidConfigurationDTO plaidConfigurationDTO2 = new PlaidConfigurationDTO();
        assertThat(plaidConfigurationDTO1).isNotEqualTo(plaidConfigurationDTO2);
        plaidConfigurationDTO2.setId(plaidConfigurationDTO1.getId());
        assertThat(plaidConfigurationDTO1).isEqualTo(plaidConfigurationDTO2);
        plaidConfigurationDTO2.setId(2L);
        assertThat(plaidConfigurationDTO1).isNotEqualTo(plaidConfigurationDTO2);
        plaidConfigurationDTO1.setId(null);
        assertThat(plaidConfigurationDTO1).isNotEqualTo(plaidConfigurationDTO2);
    }
}
