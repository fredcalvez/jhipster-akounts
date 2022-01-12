package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankStreamDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankStreamDTO.class);
        BankStreamDTO bankStreamDTO1 = new BankStreamDTO();
        bankStreamDTO1.setId(1L);
        BankStreamDTO bankStreamDTO2 = new BankStreamDTO();
        assertThat(bankStreamDTO1).isNotEqualTo(bankStreamDTO2);
        bankStreamDTO2.setId(bankStreamDTO1.getId());
        assertThat(bankStreamDTO1).isEqualTo(bankStreamDTO2);
        bankStreamDTO2.setId(2L);
        assertThat(bankStreamDTO1).isNotEqualTo(bankStreamDTO2);
        bankStreamDTO1.setId(null);
        assertThat(bankStreamDTO1).isNotEqualTo(bankStreamDTO2);
    }
}
