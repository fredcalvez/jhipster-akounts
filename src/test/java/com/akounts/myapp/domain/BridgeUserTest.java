package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeUser.class);
        BridgeUser bridgeUser1 = new BridgeUser();
        bridgeUser1.setId(1L);
        BridgeUser bridgeUser2 = new BridgeUser();
        bridgeUser2.setId(bridgeUser1.getId());
        assertThat(bridgeUser1).isEqualTo(bridgeUser2);
        bridgeUser2.setId(2L);
        assertThat(bridgeUser1).isNotEqualTo(bridgeUser2);
        bridgeUser1.setId(null);
        assertThat(bridgeUser1).isNotEqualTo(bridgeUser2);
    }
}
