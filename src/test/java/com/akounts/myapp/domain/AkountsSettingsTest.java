package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkountsSettingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkountsSettings.class);
        AkountsSettings akountsSettings1 = new AkountsSettings();
        akountsSettings1.setId(1L);
        AkountsSettings akountsSettings2 = new AkountsSettings();
        akountsSettings2.setId(akountsSettings1.getId());
        assertThat(akountsSettings1).isEqualTo(akountsSettings2);
        akountsSettings2.setId(2L);
        assertThat(akountsSettings1).isNotEqualTo(akountsSettings2);
        akountsSettings1.setId(null);
        assertThat(akountsSettings1).isNotEqualTo(akountsSettings2);
    }
}
