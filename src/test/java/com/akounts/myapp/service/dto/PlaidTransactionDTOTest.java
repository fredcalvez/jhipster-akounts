package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaidTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaidTransactionDTO.class);
        PlaidTransactionDTO plaidTransactionDTO1 = new PlaidTransactionDTO();
        plaidTransactionDTO1.setId(1L);
        PlaidTransactionDTO plaidTransactionDTO2 = new PlaidTransactionDTO();
        assertThat(plaidTransactionDTO1).isNotEqualTo(plaidTransactionDTO2);
        plaidTransactionDTO2.setId(plaidTransactionDTO1.getId());
        assertThat(plaidTransactionDTO1).isEqualTo(plaidTransactionDTO2);
        plaidTransactionDTO2.setId(2L);
        assertThat(plaidTransactionDTO1).isNotEqualTo(plaidTransactionDTO2);
        plaidTransactionDTO1.setId(null);
        assertThat(plaidTransactionDTO1).isNotEqualTo(plaidTransactionDTO2);
    }
}
