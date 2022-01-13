package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankProjectDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankProjectDTO.class);
        BankProjectDTO bankProjectDTO1 = new BankProjectDTO();
        bankProjectDTO1.setId(1L);
        BankProjectDTO bankProjectDTO2 = new BankProjectDTO();
        assertThat(bankProjectDTO1).isNotEqualTo(bankProjectDTO2);
        bankProjectDTO2.setId(bankProjectDTO1.getId());
        assertThat(bankProjectDTO1).isEqualTo(bankProjectDTO2);
        bankProjectDTO2.setId(2L);
        assertThat(bankProjectDTO1).isNotEqualTo(bankProjectDTO2);
        bankProjectDTO1.setId(null);
        assertThat(bankProjectDTO1).isNotEqualTo(bankProjectDTO2);
    }
}
