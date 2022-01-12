package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bridge.class);
        Bridge bridge1 = new Bridge();
        bridge1.setId(1L);
        Bridge bridge2 = new Bridge();
        bridge2.setId(bridge1.getId());
        assertThat(bridge1).isEqualTo(bridge2);
        bridge2.setId(2L);
        assertThat(bridge1).isNotEqualTo(bridge2);
        bridge1.setId(null);
        assertThat(bridge1).isNotEqualTo(bridge2);
    }
}
