package com.akounts.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkountsSettingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkountsSettingsDTO.class);
        AkountsSettingsDTO akountsSettingsDTO1 = new AkountsSettingsDTO();
        akountsSettingsDTO1.setId(1L);
        AkountsSettingsDTO akountsSettingsDTO2 = new AkountsSettingsDTO();
        assertThat(akountsSettingsDTO1).isNotEqualTo(akountsSettingsDTO2);
        akountsSettingsDTO2.setId(akountsSettingsDTO1.getId());
        assertThat(akountsSettingsDTO1).isEqualTo(akountsSettingsDTO2);
        akountsSettingsDTO2.setId(2L);
        assertThat(akountsSettingsDTO1).isNotEqualTo(akountsSettingsDTO2);
        akountsSettingsDTO1.setId(null);
        assertThat(akountsSettingsDTO1).isNotEqualTo(akountsSettingsDTO2);
    }
}
