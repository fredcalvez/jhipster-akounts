package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeAccount.class);
        BridgeAccount bridgeAccount1 = new BridgeAccount();
        bridgeAccount1.setId(1L);
        BridgeAccount bridgeAccount2 = new BridgeAccount();
        bridgeAccount2.setId(bridgeAccount1.getId());
        assertThat(bridgeAccount1).isEqualTo(bridgeAccount2);
        bridgeAccount2.setId(2L);
        assertThat(bridgeAccount1).isNotEqualTo(bridgeAccount2);
        bridgeAccount1.setId(null);
        assertThat(bridgeAccount1).isNotEqualTo(bridgeAccount2);
    }
}
