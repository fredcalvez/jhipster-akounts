package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidDTO.class);
        PlaidDTO plaidDTO1 = new PlaidDTO();
        plaidDTO1.setId(1L);
        PlaidDTO plaidDTO2 = new PlaidDTO();
        assertThat(plaidDTO1).isNotEqualTo(plaidDTO2);
        plaidDTO2.setId(plaidDTO1.getId());
        assertThat(plaidDTO1).isEqualTo(plaidDTO2);
        plaidDTO2.setId(2L);
        assertThat(plaidDTO1).isNotEqualTo(plaidDTO2);
        plaidDTO1.setId(null);
        assertThat(plaidDTO1).isNotEqualTo(plaidDTO2);
    }
}
