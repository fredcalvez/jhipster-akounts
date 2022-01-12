package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeTransactionDTO.class);
        BridgeTransactionDTO bridgeTransactionDTO1 = new BridgeTransactionDTO();
        bridgeTransactionDTO1.setId(1L);
        BridgeTransactionDTO bridgeTransactionDTO2 = new BridgeTransactionDTO();
        assertThat(bridgeTransactionDTO1).isNotEqualTo(bridgeTransactionDTO2);
        bridgeTransactionDTO2.setId(bridgeTransactionDTO1.getId());
        assertThat(bridgeTransactionDTO1).isEqualTo(bridgeTransactionDTO2);
        bridgeTransactionDTO2.setId(2L);
        assertThat(bridgeTransactionDTO1).isNotEqualTo(bridgeTransactionDTO2);
        bridgeTransactionDTO1.setId(null);
        assertThat(bridgeTransactionDTO1).isNotEqualTo(bridgeTransactionDTO2);
    }
}
