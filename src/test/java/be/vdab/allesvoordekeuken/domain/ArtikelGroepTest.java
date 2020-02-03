package be.vdab.allesvoordekeuken.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ArtikelGroepTest {
    private Artikel artikel1;
    private ArtikelGroep artikelGroep1, artikelGroep2;

    @BeforeEach
    void beforeEach() {
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("test2");
        artikel1 = new FoodArtikel("test", BigDecimal.TEN, BigDecimal.TEN, 5, artikelGroep1);
    }
    @Test
    void artikel1HasArtikelgroep1() {
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel1);
        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep1);
    }
    @Test
    void artikel1VerhuistNaarArtikelgroep2() {
        assertThat(artikelGroep2.addArtikel(artikel1)).isTrue();
        assertThat(artikelGroep1.getArtikels()).isEmpty();
        assertThat(artikelGroep2.getArtikels()).containsOnly(artikel1);
//        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep2);
    }
    @Test
    void artikel1VervangGroupNaarNull() {
        assertThatNullPointerException().isThrownBy(() -> artikelGroep2.addArtikel(null));
    }

}