package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.ArtikelGroep;

import java.util.Optional;

public interface ArtikelGroepRepository {
    Optional<ArtikelGroep> findById(long id);
    void create(ArtikelGroep artikelGroep);
}
