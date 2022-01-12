package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidItemDTO.class);
        PlaidItemDTO plaidItemDTO1 = new PlaidItemDTO();
        plaidItemDTO1.setId(1L);
        PlaidItemDTO plaidItemDTO2 = new PlaidItemDTO();
        assertThat(plaidItemDTO1).isNotEqualTo(plaidItemDTO2);
        plaidItemDTO2.setId(plaidItemDTO1.getId());
        assertThat(plaidItemDTO1).isEqualTo(plaidItemDTO2);
        plaidItemDTO2.setId(2L);
        assertThat(plaidItemDTO1).isNotEqualTo(plaidItemDTO2);
        plaidItemDTO1.setId(null);
        assertThat(plaidItemDTO1).isNotEqualTo(plaidItemDTO2);
    }
}
