package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidRunDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidRunDTO.class);
        PlaidRunDTO plaidRunDTO1 = new PlaidRunDTO();
        plaidRunDTO1.setId(1L);
        PlaidRunDTO plaidRunDTO2 = new PlaidRunDTO();
        assertThat(plaidRunDTO1).isNotEqualTo(plaidRunDTO2);
        plaidRunDTO2.setId(plaidRunDTO1.getId());
        assertThat(plaidRunDTO1).isEqualTo(plaidRunDTO2);
        plaidRunDTO2.setId(2L);
        assertThat(plaidRunDTO1).isNotEqualTo(plaidRunDTO2);
        plaidRunDTO1.setId(null);
        assertThat(plaidRunDTO1).isNotEqualTo(plaidRunDTO2);
    }
}
