package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankCategoryDTO.class);
        BankCategoryDTO bankCategoryDTO1 = new BankCategoryDTO();
        bankCategoryDTO1.setId(1L);
        BankCategoryDTO bankCategoryDTO2 = new BankCategoryDTO();
        assertThat(bankCategoryDTO1).isNotEqualTo(bankCategoryDTO2);
        bankCategoryDTO2.setId(bankCategoryDTO1.getId());
        assertThat(bankCategoryDTO1).isEqualTo(bankCategoryDTO2);
        bankCategoryDTO2.setId(2L);
        assertThat(bankCategoryDTO1).isNotEqualTo(bankCategoryDTO2);
        bankCategoryDTO1.setId(null);
        assertThat(bankCategoryDTO1).isNotEqualTo(bankCategoryDTO2);
    }
}
