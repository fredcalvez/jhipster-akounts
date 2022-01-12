package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeTransaction.class);
        BridgeTransaction bridgeTransaction1 = new BridgeTransaction();
        bridgeTransaction1.setId(1L);
        BridgeTransaction bridgeTransaction2 = new BridgeTransaction();
        bridgeTransaction2.setId(bridgeTransaction1.getId());
        assertThat(bridgeTransaction1).isEqualTo(bridgeTransaction2);
        bridgeTransaction2.setId(2L);
        assertThat(bridgeTransaction1).isNotEqualTo(bridgeTransaction2);
        bridgeTransaction1.setId(null);
        assertThat(bridgeTransaction1).isNotEqualTo(bridgeTransaction2);
    }
}
