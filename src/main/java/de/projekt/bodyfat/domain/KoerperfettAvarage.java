package de.projekt.bodyfat.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A KoerperfettAvarage.
 */
@Entity
@Table(name = "koerperfett_avarage")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KoerperfettAvarage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "geschlecht")
    private String geschlecht;

    @Column(name = "jhi_alter")
    private Integer alter;

    @Column(name = "koerperfettanteil")
    private Double koerperfettanteil;

    @Column(name = "datumund_zeit")
    private Instant datumundZeit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KoerperfettAvarage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeschlecht() {
        return this.geschlecht;
    }

    public KoerperfettAvarage geschlecht(String geschlecht) {
        this.setGeschlecht(geschlecht);
        return this;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public Integer getAlter() {
        return this.alter;
    }

    public KoerperfettAvarage alter(Integer alter) {
        this.setAlter(alter);
        return this;
    }

    public void setAlter(Integer alter) {
        this.alter = alter;
    }

    public Double getKoerperfettanteil() {
        return this.koerperfettanteil;
    }

    public KoerperfettAvarage koerperfettanteil(Double koerperfettanteil) {
        this.setKoerperfettanteil(koerperfettanteil);
        return this;
    }

    public void setKoerperfettanteil(Double koerperfettanteil) {
        this.koerperfettanteil = koerperfettanteil;
    }

    public Instant getDatumundZeit() {
        return this.datumundZeit;
    }

    public KoerperfettAvarage datumundZeit(Instant datumundZeit) {
        this.setDatumundZeit(datumundZeit);
        return this;
    }

    public void setDatumundZeit(Instant datumundZeit) {
        this.datumundZeit = datumundZeit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KoerperfettAvarage)) {
            return false;
        }
        return id != null && id.equals(((KoerperfettAvarage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KoerperfettAvarage{" +
            "id=" + getId() +
            ", geschlecht='" + getGeschlecht() + "'" +
            ", alter=" + getAlter() +
            ", koerperfettanteil=" + getKoerperfettanteil() +
            ", datumundZeit='" + getDatumundZeit() + "'" +
            "}";
    }
}
