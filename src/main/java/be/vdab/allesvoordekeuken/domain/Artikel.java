package be.vdab.allesvoordekeuken.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "artikels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "soort")
public abstract class Artikel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private BigDecimal aankoopprijs;
    private BigDecimal verkoopprijs;
    @ElementCollection
    @CollectionTable(name = "kortingen", joinColumns = @JoinColumn(name = "artikelid"))
    @OrderBy("vanafAantal")
    private Set<Korting> kortings;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "artikelgroepid")
    private ArtikelGroep artikelGroep;

    protected Artikel() {
    }

    public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, ArtikelGroep artikelGroep) {
        this.naam = naam;
        this.aankoopprijs = aankoopprijs;
        this.verkoopprijs = verkoopprijs;
        this.kortings = new LinkedHashSet<>();
        setArtikelGroep(artikelGroep);
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public BigDecimal getAankoopprijs() {
        return aankoopprijs;
    }

    public BigDecimal getVerkoopprijs() {
        return verkoopprijs;
    }

    public Set<Korting> getKortings() {
        return Collections.unmodifiableSet(kortings);
    }

    public boolean addKorting(Korting korting) {
        return kortings.add(korting);
    }

    public boolean removeKorting(Korting korting) {
        return removeKorting(korting);
    }

    public ArtikelGroep getArtikelGroep() {
        return artikelGroep;
    }

    public void setArtikelGroep(ArtikelGroep artikelGroep) {
//        if(artikelGroep == null) {
//            throw new NullPointerException();
//        }
        if(!artikelGroep.getArtikels().contains(this)) {
            artikelGroep.addArtikel(this);
        }
        this.artikelGroep = artikelGroep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artikel)) return false;
        Artikel artikel = (Artikel) o;
        return naam.toLowerCase().equalsIgnoreCase(artikel.naam.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam.toLowerCase());
    }


}
