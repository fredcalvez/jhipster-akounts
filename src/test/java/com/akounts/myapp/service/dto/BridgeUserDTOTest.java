package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BridgeUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridgeUserDTO.class);
        BridgeUserDTO bridgeUserDTO1 = new BridgeUserDTO();
        bridgeUserDTO1.setId(1L);
        BridgeUserDTO bridgeUserDTO2 = new BridgeUserDTO();
        assertThat(bridgeUserDTO1).isNotEqualTo(bridgeUserDTO2);
        bridgeUserDTO2.setId(bridgeUserDTO1.getId());
        assertThat(bridgeUserDTO1).isEqualTo(bridgeUserDTO2);
        bridgeUserDTO2.setId(2L);
        assertThat(bridgeUserDTO1).isNotEqualTo(bridgeUserDTO2);
        bridgeUserDTO1.setId(null);
        assertThat(bridgeUserDTO1).isNotEqualTo(bridgeUserDTO2);
    }
}
