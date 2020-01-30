package be.vdab.allesvoordekeuken.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import be.vdab.allesvoordekeuken.domain.Artikel;
import be.vdab.allesvoordekeuken.domain.FoodArtikel;
import be.vdab.allesvoordekeuken.domain.NonFoodArtikel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private static final String ARTIKELS = "artikels";
//    private Artikel artikel;

   /* @BeforeEach
    void beforeEach() {
        artikel = new Artikel("test", BigDecimal.ONE, BigDecimal.TEN);
    }*/

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
    }

    private long idVanTestFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam= 'testFoodArtikel'", Long.class);
    }
    private long idVanNonFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam= 'testNonFoodArtikel'", Long.class);
    }

    /*@Test
    void findById() {
        long id = idVanTestA();
        assertThat(repository.findById(id).get().getNaam()).isEqualTo("testA");
    }*/
    @Test
    void findFoodArtikelById() {
        long id = idVanTestFoodArtikel();
        assertThat(((FoodArtikel)repository.findById(id).get()).getHoudbaarheid()).isEqualTo(1);
    }
    @Test
    void findNonFoodArtikelById() {
        long id = idVanNonFoodArtikel();
        assertThat(((NonFoodArtikel)repository.findById(id).get()).getGarantie()).isEqualTo(6);
    }


    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    /*@Test
    void create() {
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id= " + artikel.getId())).isOne();
    }*/
    @Test
    void createFoodArtikel() {
        FoodArtikel foodArtikel = new FoodArtikel("testFoodArtikel2", BigDecimal.TEN, BigDecimal.TEN, 2);
        repository.create(foodArtikel);
        assertThat(foodArtikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id= " + foodArtikel.getId())).isOne();
    }
    @Test
    void createNonFoodArtikel() {
        NonFoodArtikel nonFoodArtikel = new NonFoodArtikel("testFoodArtikel2", BigDecimal.TEN, BigDecimal.TEN, 5);
        repository.create(nonFoodArtikel);
        assertThat(nonFoodArtikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id= " + nonFoodArtikel.getId())).isOne();
    }

    @Test
    void findByWoordInNaam() {
        /*String woord = "te";
        assertThat(repository.findByWoordInNaam(woord))
                .hasSize(super.countRowsInTableWhere(ARTIKELS, "naam like '%"+ woord+ "%'"))
                .allSatisfy(artikel -> assertThat(artikel.getNaam()).contains(woord));*/
        assertThat(repository.findByWoordInNaam("te"))
                .hasSize(super.jdbcTemplate.queryForObject("select count(*) from artikels where naam like '%te%'", Integer.class))
                .extracting(artikel -> artikel.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam).contains("te"))
                .isSorted();
    }

//    @Test
//    void findByLeegWoordInNaamIsVerkeerd() {
//        assertThatIllegalArgumentException().isThrownBy(() -> repository.findByWoordInNaam(""));
//    }
//
//    @Test
//    void findByOnbestaandeWoordInNaamIsVerkeerd() {
//        assertThatNullPointerException().isThrownBy(() -> repository.findByWoordInNaam(null));
//    }
    @Test
    void algemeneVerhoog() {
        assertThat(repository.algemeneVerhoog(BigDecimal.TEN))
                .isEqualTo(super.countRowsInTable(ARTIKELS));
        assertThat(super.jdbcTemplate.queryForObject(
                "select verkoopprijs from artikels where id = ?", BigDecimal.class, idVanTestFoodArtikel()))
                .isEqualByComparingTo("11");
    }
}