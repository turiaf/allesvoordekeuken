package be.vdab.allesvoordekeuken.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "artikelgroepen")
public class ArtikelGroep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToMany(mappedBy = "artikelGroep")
    @OrderBy("naam")
    private Set<Artikel> artikels;

    protected ArtikelGroep() {
    }
    public ArtikelGroep(String naam) {
//        if(naam.trim().isEmpty()) {
//            throw new IllegalArgumentException();
//        }
        this.naam = naam;
        artikels = new LinkedHashSet<>();
    }
    public boolean addArtikel(Artikel artikel) {
        boolean toevoegen = artikels.add(artikel);
        ArtikelGroep oudeArtikelGroep = artikel.getArtikelGroep();
//        if(oudeArtikelGroep != null && oudeArtikelGroep.getArtikels().contains(artikel)) {
//            oudeArtikelGroep.artikels.remove(artikel);
//        }
        if(oudeArtikelGroep != null && oudeArtikelGroep != this) {
            oudeArtikelGroep.artikels.remove(artikel);
        }
        if(oudeArtikelGroep != null) {
            artikel.setArtikelGroep(this);
        }
        return toevoegen;
    }

    public Set<Artikel> getArtikels() {
        return Collections.unmodifiableSet(artikels);
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
