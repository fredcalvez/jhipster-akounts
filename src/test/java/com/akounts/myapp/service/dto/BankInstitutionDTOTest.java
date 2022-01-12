package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankInstitutionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankInstitutionDTO.class);
        BankInstitutionDTO bankInstitutionDTO1 = new BankInstitutionDTO();
        bankInstitutionDTO1.setId(1L);
        BankInstitutionDTO bankInstitutionDTO2 = new BankInstitutionDTO();
        assertThat(bankInstitutionDTO1).isNotEqualTo(bankInstitutionDTO2);
        bankInstitutionDTO2.setId(bankInstitutionDTO1.getId());
        assertThat(bankInstitutionDTO1).isEqualTo(bankInstitutionDTO2);
        bankInstitutionDTO2.setId(2L);
        assertThat(bankInstitutionDTO1).isNotEqualTo(bankInstitutionDTO2);
        bankInstitutionDTO1.setId(null);
        assertThat(bankInstitutionDTO1).isNotEqualTo(bankInstitutionDTO2);
    }
}
