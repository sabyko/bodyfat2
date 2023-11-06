package de.projekt.bodyfat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.projekt.bodyfat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KoerperfettTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Koerperfett.class);
        Koerperfett koerperfett1 = new Koerperfett();
        koerperfett1.setId(1L);
        Koerperfett koerperfett2 = new Koerperfett();
        koerperfett2.setId(koerperfett1.getId());
        assertThat(koerperfett1).isEqualTo(koerperfett2);
        koerperfett2.setId(2L);
        assertThat(koerperfett1).isNotEqualTo(koerperfett2);
        koerperfett1.setId(null);
        assertThat(koerperfett1).isNotEqualTo(koerperfett2);
    }
}
