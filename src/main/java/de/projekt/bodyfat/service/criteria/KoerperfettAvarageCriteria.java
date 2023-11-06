package de.projekt.bodyfat.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link de.projekt.bodyfat.domain.KoerperfettAvarage} entity. This class is used
 * in {@link de.projekt.bodyfat.web.rest.KoerperfettAvarageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /koerperfett-avarages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KoerperfettAvarageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter geschlecht;

    private IntegerFilter alter;

    private DoubleFilter koerperfettanteil;

    private InstantFilter datumundZeit;

    private Boolean distinct;

    public KoerperfettAvarageCriteria() {}

    public KoerperfettAvarageCriteria(KoerperfettAvarageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.geschlecht = other.geschlecht == null ? null : other.geschlecht.copy();
        this.alter = other.alter == null ? null : other.alter.copy();
        this.koerperfettanteil = other.koerperfettanteil == null ? null : other.koerperfettanteil.copy();
        this.datumundZeit = other.datumundZeit == null ? null : other.datumundZeit.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KoerperfettAvarageCriteria copy() {
        return new KoerperfettAvarageCriteria(this);
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

    public IntegerFilter getAlter() {
        return alter;
    }

    public IntegerFilter alter() {
        if (alter == null) {
            alter = new IntegerFilter();
        }
        return alter;
    }

    public void setAlter(IntegerFilter alter) {
        this.alter = alter;
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
        final KoerperfettAvarageCriteria that = (KoerperfettAvarageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(geschlecht, that.geschlecht) &&
            Objects.equals(alter, that.alter) &&
            Objects.equals(koerperfettanteil, that.koerperfettanteil) &&
            Objects.equals(datumundZeit, that.datumundZeit) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, geschlecht, alter, koerperfettanteil, datumundZeit, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KoerperfettAvarageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (geschlecht != null ? "geschlecht=" + geschlecht + ", " : "") +
            (alter != null ? "alter=" + alter + ", " : "") +
            (koerperfettanteil != null ? "koerperfettanteil=" + koerperfettanteil + ", " : "") +
            (datumundZeit != null ? "datumundZeit=" + datumundZeit + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
