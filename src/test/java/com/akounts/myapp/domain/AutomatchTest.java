package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutomatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Automatch.class);
        Automatch automatch1 = new Automatch();
        automatch1.setId(1L);
        Automatch automatch2 = new Automatch();
        automatch2.setId(automatch1.getId());
        assertThat(automatch1).isEqualTo(automatch2);
        automatch2.setId(2L);
        assertThat(automatch1).isNotEqualTo(automatch2);
        automatch1.setId(null);
        assertThat(automatch1).isNotEqualTo(automatch2);
    }
}
