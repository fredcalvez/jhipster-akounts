package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeRunTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeRun.class);
        BridgeRun bridgeRun1 = new BridgeRun();
        bridgeRun1.setId(1L);
        BridgeRun bridgeRun2 = new BridgeRun();
        bridgeRun2.setId(bridgeRun1.getId());
        assertThat(bridgeRun1).isEqualTo(bridgeRun2);
        bridgeRun2.setId(2L);
        assertThat(bridgeRun1).isNotEqualTo(bridgeRun2);
        bridgeRun1.setId(null);
        assertThat(bridgeRun1).isNotEqualTo(bridgeRun2);
    }
}
