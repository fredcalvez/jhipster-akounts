package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidAccountDTO.class);
        PlaidAccountDTO plaidAccountDTO1 = new PlaidAccountDTO();
        plaidAccountDTO1.setId(1L);
        PlaidAccountDTO plaidAccountDTO2 = new PlaidAccountDTO();
        assertThat(plaidAccountDTO1).isNotEqualTo(plaidAccountDTO2);
        plaidAccountDTO2.setId(plaidAccountDTO1.getId());
        assertThat(plaidAccountDTO1).isEqualTo(plaidAccountDTO2);
        plaidAccountDTO2.setId(2L);
        assertThat(plaidAccountDTO1).isNotEqualTo(plaidAccountDTO2);
        plaidAccountDTO1.setId(null);
        assertThat(plaidAccountDTO1).isNotEqualTo(plaidAccountDTO2);
    }
}
