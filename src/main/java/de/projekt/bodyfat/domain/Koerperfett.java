package de.projekt.bodyfat.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Koerperfett.
 */
@Entity
@Table(name = "koerperfett")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Koerperfett implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "privatoderfirma", nullable = false)
    private Boolean privatoderfirma;

    @NotNull
    @Column(name = "koerpergroesse", nullable = false)
    private Integer koerpergroesse;

    @NotNull
    @Column(name = "nackenumfang", nullable = false)
    private Integer nackenumfang;

    @NotNull
    @Column(name = "bauchumfang", nullable = false)
    private Integer bauchumfang;

    @Column(name = "hueftumfang")
    private Integer hueftumfang;

    @NotNull
    @Column(name = "geschlecht", nullable = false)
    private String geschlecht;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "koerperfettanteil")
    private Double koerperfettanteil;

    @Column(name = "datumund_zeit")
    private Instant datumundZeit;

    @Column(name = "url")
    private String url;

    @Column(name = "success")
    private Boolean success;

    @Column(name = "error_message")
    private String errorMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Koerperfett id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPrivatoderfirma() {
        return this.privatoderfirma;
    }

    public Koerperfett privatoderfirma(Boolean privatoderfirma) {
        this.setPrivatoderfirma(privatoderfirma);
        return this;
    }

    public void setPrivatoderfirma(Boolean privatoderfirma) {
        this.privatoderfirma = privatoderfirma;
    }

    public Integer getKoerpergroesse() {
        return this.koerpergroesse;
    }

    public Koerperfett koerpergroesse(Integer koerpergroesse) {
        this.setKoerpergroesse(koerpergroesse);
        return this;
    }

    public void setKoerpergroesse(Integer koerpergroesse) {
        this.koerpergroesse = koerpergroesse;
    }

    public Integer getNackenumfang() {
        return this.nackenumfang;
    }

    public Koerperfett nackenumfang(Integer nackenumfang) {
        this.setNackenumfang(nackenumfang);
        return this;
    }

    public void setNackenumfang(Integer nackenumfang) {
        this.nackenumfang = nackenumfang;
    }

    public Integer getBauchumfang() {
        return this.bauchumfang;
    }

    public Koerperfett bauchumfang(Integer bauchumfang) {
        this.setBauchumfang(bauchumfang);
        return this;
    }

    public void setBauchumfang(Integer bauchumfang) {
        this.bauchumfang = bauchumfang;
    }

    public Integer getHueftumfang() {
        return this.hueftumfang;
    }

    public Koerperfett hueftumfang(Integer hueftumfang) {
        this.setHueftumfang(hueftumfang);
        return this;
    }

    public void setHueftumfang(Integer hueftumfang) {
        this.hueftumfang = hueftumfang;
    }

    public String getGeschlecht() {
        return this.geschlecht;
    }

    public Koerperfett geschlecht(String geschlecht) {
        this.setGeschlecht(geschlecht);
        return this;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public Integer getAge() {
        return this.age;
    }

    public Koerperfett age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getKoerperfettanteil() {
        return this.koerperfettanteil;
    }

    public Koerperfett koerperfettanteil(Double koerperfettanteil) {
        this.setKoerperfettanteil(koerperfettanteil);
        return this;
    }

    public void setKoerperfettanteil(Double koerperfettanteil) {
        this.koerperfettanteil = koerperfettanteil;
    }

    public Instant getDatumundZeit() {
        return this.datumundZeit;
    }

    public Koerperfett datumundZeit(Instant datumundZeit) {
        this.setDatumundZeit(datumundZeit);
        return this;
    }

    public void setDatumundZeit(Instant datumundZeit) {
        this.datumundZeit = datumundZeit;
    }

    public String getUrl() {
        return this.url;
    }

    public Koerperfett url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public Koerperfett success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Koerperfett errorMessage(String errorMessage) {
        this.setErrorMessage(errorMessage);
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Koerperfett)) {
            return false;
        }
        return id != null && id.equals(((Koerperfett) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Koerperfett{" +
            "id=" + getId() +
            ", privatoderfirma='" + getPrivatoderfirma() + "'" +
            ", koerpergroesse=" + getKoerpergroesse() +
            ", nackenumfang=" + getNackenumfang() +
            ", bauchumfang=" + getBauchumfang() +
            ", hueftumfang=" + getHueftumfang() +
            ", geschlecht='" + getGeschlecht() + "'" +
            ", age=" + getAge() +
            ", koerperfettanteil=" + getKoerperfettanteil() +
            ", datumundZeit='" + getDatumundZeit() + "'" +
            ", url='" + getUrl() + "'" +
            ", success='" + getSuccess() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            "}";
    }
}
