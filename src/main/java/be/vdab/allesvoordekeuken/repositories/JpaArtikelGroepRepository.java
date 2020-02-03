package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.ArtikelGroep;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
class JpaArtikelGroepRepository implements ArtikelGroepRepository {
    private final EntityManager manager;

    public JpaArtikelGroepRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<ArtikelGroep> findById(long id) {
        return Optional.ofNullable(manager.find(ArtikelGroep.class, id));
    }

    @Override
    public void create(ArtikelGroep artikelGroep) {
        manager.persist(artikelGroep);
    }
}
