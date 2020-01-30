package be.vdab.allesvoordekeuken.domain;

import javax.persistence.*;
import java.math.BigDecimal;

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

    protected Artikel() {
    }

    public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs) {
        this.naam = naam;
        this.aankoopprijs = aankoopprijs;
        this.verkoopprijs = verkoopprijs;
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
}
