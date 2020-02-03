package be.vdab.allesvoordekeuken.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import be.vdab.allesvoordekeuken.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/insertArtikelgroepen.sql")
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private static final String ARTIKELS = "artikels";
    private ArtikelGroep artikelGroep;
    private final EntityManager manager;
//    private Artikel artikel;

   /* @BeforeEach
    void beforeEach() {
        artikel = new Artikel("test", BigDecimal.ONE, BigDecimal.TEN);
    }*/

//    public JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
//        this.repository = repository;
//    }

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @BeforeEach
    void beforeEach() {
        artikelGroep = new ArtikelGroep("test3");
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
//        FoodArtikel foodArtikel = new FoodArtikel(
//                "testFoodArtikel2", BigDecimal.TEN, BigDecimal.TEN, 2, artikelGroep);
//        repository.create(foodArtikel);
//        manager.flush();
//        assertThat(foodArtikel.getId()).isPositive();
//        assertThat(super.countRowsInTableWhere(ARTIKELS, "id= " + foodArtikel.getId())).isOne();
        ArtikelGroep artikelGroep = new ArtikelGroep("test3");
        manager.persist(artikelGroep);
        Artikel artikel = new FoodArtikel(
                "testFoodArtikel2", BigDecimal.TEN, BigDecimal.TEN, 2, artikelGroep);
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id= " + artikel.getId())).isOne();
    }
    @Test
    void createNonFoodArtikel() {

//        NonFoodArtikel nonFoodArtikel = new NonFoodArtikel("testFoodArtikel2", BigDecimal.TEN, BigDecimal.TEN, 5, artikelGroep);
//        manager.persist(artikelGroep);
//        repository.create(nonFoodArtikel);
//        manager.flush();
        ArtikelGroep artikelGroep = new ArtikelGroep("test3");
        manager.persist(artikelGroep);
        Artikel artikel = new NonFoodArtikel("testFoodArtikel2", BigDecimal.TEN, BigDecimal.TEN, 5, artikelGroep);
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id= " + artikel.getId())).isOne();
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

    @Test
    void lezenKorting() {
        Artikel artikel = repository.findById(idVanTestFoodArtikel()).get();
        assertThat(artikel.getKortings()).containsOnly(new Korting(2, BigDecimal.TEN));
    }

    @Test
    void artikelGroepLazyLoaded() {
        assertThat(repository.findById(idVanTestFoodArtikel()).get()
                .getArtikelGroep().getNaam()).isEqualTo("test");
    }
}