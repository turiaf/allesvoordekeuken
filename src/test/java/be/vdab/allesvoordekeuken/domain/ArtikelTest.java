package be.vdab.allesvoordekeuken.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ArtikelTest {
    private Artikel artikel1, nogEenArtikel1, artikel2;
    private ArtikelGroep artikelGroep1;
    private ArtikelGroep artikelGroep2;

    @BeforeEach
    void beforeEach() {
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("test2");
        artikel1 = new FoodArtikel("test", BigDecimal.TEN, BigDecimal.TEN, 5, artikelGroep1);
    }

    @Test
    void artikel1EnArtikelgroep1ZijnVerbonden() {
        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel1);
    }
    @Test
    void artikel1VerhuistNaarNaarArtikelgroep2() {
        artikel1.setArtikelGroep(artikelGroep2);
//        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep2);
        assertThat(artikelGroep1.getArtikels()).isEmpty();
        assertThat(artikelGroep2.getArtikels()).containsOnly(artikel1);
    }
    @Test
    void nullAlsArtikelGroepInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(() -> artikel1.setArtikelGroep(null));
    }
    @Test
    void nullAlsArtikelGroepInDeConstructorMislukt() {
        assertThatNullPointerException().isThrownBy(() ->
                new FoodArtikel("test", BigDecimal.TEN, BigDecimal.ONE, 1, null));
    }
}