package com.akounts.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akounts.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TextCleanerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextCleaner.class);
        TextCleaner textCleaner1 = new TextCleaner();
        textCleaner1.setId(1L);
        TextCleaner textCleaner2 = new TextCleaner();
        textCleaner2.setId(textCleaner1.getId());
        assertThat(textCleaner1).isEqualTo(textCleaner2);
        textCleaner2.setId(2L);
        assertThat(textCleaner1).isNotEqualTo(textCleaner2);
        textCleaner1.setId(null);
        assertThat(textCleaner1).isNotEqualTo(textCleaner2);
    }
}
