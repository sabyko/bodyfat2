package de.projekt.bodyfat.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link de.projekt.bodyfat.domain.Koerperfett} entity. This class is used
 * in {@link de.projekt.bodyfat.web.rest.KoerperfettResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /koerperfetts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KoerperfettCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter privatoderfirma;

    private IntegerFilter koerpergroesse;

    private IntegerFilter nackenumfang;

    private IntegerFilter bauchumfang;

    private IntegerFilter hueftumfang;

    private StringFilter geschlecht;

    private IntegerFilter age;

    private DoubleFilter koerperfettanteil;

    private InstantFilter datumundZeit;

    private StringFilter url;

    private BooleanFilter success;

    private StringFilter errorMessage;

    private Boolean distinct;

    public KoerperfettCriteria() {}

    public KoerperfettCriteria(KoerperfettCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.privatoderfirma = other.privatoderfirma == null ? null : other.privatoderfirma.copy();
        this.koerpergroesse = other.koerpergroesse == null ? null : other.koerpergroesse.copy();
        this.nackenumfang = other.nackenumfang == null ? null : other.nackenumfang.copy();
        this.bauchumfang = other.bauchumfang == null ? null : other.bauchumfang.copy();
        this.hueftumfang = other.hueftumfang == null ? null : other.hueftumfang.copy();
        this.geschlecht = other.geschlecht == null ? null : other.geschlecht.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.koerperfettanteil = other.koerperfettanteil == null ? null : other.koerperfettanteil.copy();
        this.datumundZeit = other.datumundZeit == null ? null : other.datumundZeit.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.success = other.success == null ? null : other.success.copy();
        this.errorMessage = other.errorMessage == null ? null : other.errorMessage.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KoerperfettCriteria copy() {
        return new KoerperfettCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getPrivatoderfirma() {
        return privatoderfirma;
    }

    public BooleanFilter privatoderfirma() {
        if (privatoderfirma == null) {
            privatoderfirma = new BooleanFilter();
        }
        return privatoderfirma;
    }

    public void setPrivatoderfirma(BooleanFilter privatoderfirma) {
        this.privatoderfirma = privatoderfirma;
    }

    public IntegerFilter getKoerpergroesse() {
        return koerpergroesse;
    }

    public IntegerFilter koerpergroesse() {
        if (koerpergroesse == null) {
            koerpergroesse = new IntegerFilter();
        }
        return koerpergroesse;
    }

    public void setKoerpergroesse(IntegerFilter koerpergroesse) {
        this.koerpergroesse = koerpergroesse;
    }

    public IntegerFilter getNackenumfang() {
        return nackenumfang;
    }

    public IntegerFilter nackenumfang() {
        if (nackenumfang == null) {
            nackenumfang = new IntegerFilter();
        }
        return nackenumfang;
    }

    public void setNackenumfang(IntegerFilter nackenumfang) {
        this.nackenumfang = nackenumfang;
    }

    public IntegerFilter getBauchumfang() {
        return bauchumfang;
    }

    public IntegerFilter bauchumfang() {
        if (bauchumfang == null) {
            bauchumfang = new IntegerFilter();
        }
        return bauchumfang;
    }

    public void setBauchumfang(IntegerFilter bauchumfang) {
        this.bauchumfang = bauchumfang;
    }

    public IntegerFilter getHueftumfang() {
        return hueftumfang;
    }

    public IntegerFilter hueftumfang() {
        if (hueftumfang == null) {
            hueftumfang = new IntegerFilter();
        }
        return hueftumfang;
    }

    public void setHueftumfang(IntegerFilter hueftumfang) {
        this.hueftumfang = hueftumfang;
    }

    public StringFilter getGeschlecht() {
        return geschlecht;
    }

    public StringFilter geschlecht() {
        if (geschlecht == null) {
            geschlecht = new StringFilter();
        }
        return geschlecht;
    }

    public void setGeschlecht(StringFilter geschlecht) {
        this.geschlecht = geschlecht;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public IntegerFilter age() {
        if (age == null) {
            age = new IntegerFilter();
        }
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public DoubleFilter getKoerperfettanteil() {
        return koerperfettanteil;
    }

    public DoubleFilter koerperfettanteil() {
        if (koerperfettanteil == null) {
            koerperfettanteil = new DoubleFilter();
        }
        return koerperfettanteil;
    }

    public void setKoerperfettanteil(DoubleFilter koerperfettanteil) {
        this.koerperfettanteil = koerperfettanteil;
    }

    public InstantFilter getDatumundZeit() {
        return datumundZeit;
    }

    public InstantFilter datumundZeit() {
        if (datumundZeit == null) {
            datumundZeit = new InstantFilter();
        }
        return datumundZeit;
    }

    public void setDatumundZeit(InstantFilter datumundZeit) {
        this.datumundZeit = datumundZeit;
    }

    public StringFilter getUrl() {
        return url;
    }

    public StringFilter url() {
        if (url == null) {
            url = new StringFilter();
        }
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public BooleanFilter getSuccess() {
        return success;
    }

    public BooleanFilter success() {
        if (success == null) {
            success = new BooleanFilter();
        }
        return success;
    }

    public void setSuccess(BooleanFilter success) {
        this.success = success;
    }

    public StringFilter getErrorMessage() {
        return errorMessage;
    }

    public StringFilter errorMessage() {
        if (errorMessage == null) {
            errorMessage = new StringFilter();
        }
        return errorMessage;
    }

    public void setErrorMessage(StringFilter errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KoerperfettCriteria that = (KoerperfettCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(privatoderfirma, that.privatoderfirma) &&
            Objects.equals(koerpergroesse, that.koerpergroesse) &&
            Objects.equals(nackenumfang, that.nackenumfang) &&
            Objects.equals(bauchumfang, that.bauchumfang) &&
            Objects.equals(hueftumfang, that.hueftumfang) &&
            Objects.equals(geschlecht, that.geschlecht) &&
            Objects.equals(age, that.age) &&
            Objects.equals(koerperfettanteil, that.koerperfettanteil) &&
            Objects.equals(datumundZeit, that.datumundZeit) &&
            Objects.equals(url, that.url) &&
            Objects.equals(success, that.success) &&
            Objects.equals(errorMessage, that.errorMessage) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            privatoderfirma,
            koerpergroesse,
            nackenumfang,
            bauchumfang,
            hueftumfang,
            geschlecht,
            age,
            koerperfettanteil,
            datumundZeit,
            url,
            success,
            errorMessage,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KoerperfettCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (privatoderfirma != null ? "privatoderfirma=" + privatoderfirma + ", " : "") +
            (koerpergroesse != null ? "koerpergroesse=" + koerpergroesse + ", " : "") +
            (nackenumfang != null ? "nackenumfang=" + nackenumfang + ", " : "") +
            (bauchumfang != null ? "bauchumfang=" + bauchumfang + ", " : "") +
            (hueftumfang != null ? "hueftumfang=" + hueftumfang + ", " : "") +
            (geschlecht != null ? "geschlecht=" + geschlecht + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (koerperfettanteil != null ? "koerperfettanteil=" + koerperfettanteil + ", " : "") +
            (datumundZeit != null ? "datumundZeit=" + datumundZeit + ", " : "") +
            (url != null ? "url=" + url + ", " : "") +
            (success != null ? "success=" + success + ", " : "") +
            (errorMessage != null ? "errorMessage=" + errorMessage + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
