package de.projekt.bodyfat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.projekt.bodyfat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KoerperfettAvarageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KoerperfettAvarage.class);
        KoerperfettAvarage koerperfettAvarage1 = new KoerperfettAvarage();
        koerperfettAvarage1.setId(1L);
        KoerperfettAvarage koerperfettAvarage2 = new KoerperfettAvarage();
        koerperfettAvarage2.setId(koerperfettAvarage1.getId());
        assertThat(koerperfettAvarage1).isEqualTo(koerperfettAvarage2);
        koerperfettAvarage2.setId(2L);
        assertThat(koerperfettAvarage1).isNotEqualTo(koerperfettAvarage2);
        koerperfettAvarage1.setId(null);
        assertThat(koerperfettAvarage1).isNotEqualTo(koerperfettAvarage2);
    }
}
