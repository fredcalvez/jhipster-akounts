package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTagDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTagDTO.class);
        BankTagDTO bankTagDTO1 = new BankTagDTO();
        bankTagDTO1.setId(1L);
        BankTagDTO bankTagDTO2 = new BankTagDTO();
        assertThat(bankTagDTO1).isNotEqualTo(bankTagDTO2);
        bankTagDTO2.setId(bankTagDTO1.getId());
        assertThat(bankTagDTO1).isEqualTo(bankTagDTO2);
        bankTagDTO2.setId(2L);
        assertThat(bankTagDTO1).isNotEqualTo(bankTagDTO2);
        bankTagDTO1.setId(null);
        assertThat(bankTagDTO1).isNotEqualTo(bankTagDTO2);
    }
}
