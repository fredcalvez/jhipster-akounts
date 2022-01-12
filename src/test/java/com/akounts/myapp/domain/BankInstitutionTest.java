package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankInstitutionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankInstitution.class);
        BankInstitution bankInstitution1 = new BankInstitution();
        bankInstitution1.setId(1L);
        BankInstitution bankInstitution2 = new BankInstitution();
        bankInstitution2.setId(bankInstitution1.getId());
        assertThat(bankInstitution1).isEqualTo(bankInstitution2);
        bankInstitution2.setId(2L);
        assertThat(bankInstitution1).isNotEqualTo(bankInstitution2);
        bankInstitution1.setId(null);
        assertThat(bankInstitution1).isNotEqualTo(bankInstitution2);
    }
}
