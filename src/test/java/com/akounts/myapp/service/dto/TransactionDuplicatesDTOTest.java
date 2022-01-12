package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransactionDuplicatesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionDuplicatesDTO.class);
        TransactionDuplicatesDTO transactionDuplicatesDTO1 = new TransactionDuplicatesDTO();
        transactionDuplicatesDTO1.setId(1L);
        TransactionDuplicatesDTO transactionDuplicatesDTO2 = new TransactionDuplicatesDTO();
        assertThat(transactionDuplicatesDTO1).isNotEqualTo(transactionDuplicatesDTO2);
        transactionDuplicatesDTO2.setId(transactionDuplicatesDTO1.getId());
        assertThat(transactionDuplicatesDTO1).isEqualTo(transactionDuplicatesDTO2);
        transactionDuplicatesDTO2.setId(2L);
        assertThat(transactionDuplicatesDTO1).isNotEqualTo(transactionDuplicatesDTO2);
        transactionDuplicatesDTO1.setId(null);
        assertThat(transactionDuplicatesDTO1).isNotEqualTo(transactionDuplicatesDTO2);
    }
}
