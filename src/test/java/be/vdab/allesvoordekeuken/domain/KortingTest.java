package be.vdab.allesvoordekeuken.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class KortingTest {
    private Korting korting1, nogKorting1, korting2;

    @BeforeEach
    void beforeEach() {
        korting1 = new Korting(1, BigDecimal.TEN);
        nogKorting1 = new Korting(1, BigDecimal.TEN);
        korting2 = new Korting(2, BigDecimal.ONE);
    }

    @Test
    void kortingenMetDezelfdeVanafEnPercentageZijnGelijk() {
        assertThat(korting1).isEqualTo(nogKorting1);
    }
    @Test
    void kortingenMetVerchildVanafEnPercentageZijnVerchild() {
        assertThat(korting1).isNotEqualTo(korting2);
    }
    @Test
    void kortingenMetNullZijnVerchild() {
        assertThat(korting1).isNotEqualTo(null);
    }
    @Test
    void kortingenMetAndereInstanceZijnVerchild() {
        assertThat(korting1).isNotEqualTo("");
    }

    @Test
    void kortingenDieEqualZijnHebbenDeZelfdeHashcode() {
        assertThat(korting1).hasSameHashCodeAs(nogKorting1);
    }
}