package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
class JpaArtikelRepository implements ArtikelRepository {
    private final EntityManager manager;

    public JpaArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Artikel> findById(long id) {
        return Optional.ofNullable(manager.find(Artikel.class, id));
    }

    @Override
    public void create(Artikel artikel) {
        manager.persist(artikel);
    }

    @Override
    public List<Artikel> findByWoordInNaam(String woord) {
        /*if(woord.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String woor = "%"+ woord + "%";
        return manager.createQuery(
                "select a from Artikel a where a.naam like :woor order by a.naam", Artikel.class)
                .setParameter("woor", woor)
                .getResultList();*/
        return manager.createNamedQuery("Artikel.findByWoordInNaam", Artikel.class)
                .setParameter("woord", '%'+woord+ '%')
                .setHint("javax.persistence.loadgraph",
                        manager.createEntityGraph(Artikel.MET_ARTIKELGROEP))
                .getResultList();
    }

    @Override
    public int algemeneVerhoog(BigDecimal percentage) {
        BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        return manager.createNamedQuery("Artikel.algemeneVerhoog")
                .setParameter("factor", factor)
                .executeUpdate();
    }
}
