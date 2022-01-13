package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransactionDuplicatesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionDuplicates.class);
        TransactionDuplicates transactionDuplicates1 = new TransactionDuplicates();
        transactionDuplicates1.setId(1L);
        TransactionDuplicates transactionDuplicates2 = new TransactionDuplicates();
        transactionDuplicates2.setId(transactionDuplicates1.getId());
        assertThat(transactionDuplicates1).isEqualTo(transactionDuplicates2);
        transactionDuplicates2.setId(2L);
        assertThat(transactionDuplicates1).isNotEqualTo(transactionDuplicates2);
        transactionDuplicates1.setId(null);
        assertThat(transactionDuplicates1).isNotEqualTo(transactionDuplicates2);
    }
}
