package de.projekt.bodyfat.service;

import de.projekt.bodyfat.domain.*; // for static metamodels
import de.projekt.bodyfat.domain.KoerperfettAvarage;
import de.projekt.bodyfat.repository.KoerperfettAvarageRepository;
import de.projekt.bodyfat.service.criteria.KoerperfettAvarageCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link KoerperfettAvarage} entities in the database.
 * The main input is a {@link KoerperfettAvarageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KoerperfettAvarage} or a {@link Page} of {@link KoerperfettAvarage} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KoerperfettAvarageQueryService extends QueryService<KoerperfettAvarage> {

    private final Logger log = LoggerFactory.getLogger(KoerperfettAvarageQueryService.class);

    private final KoerperfettAvarageRepository koerperfettAvarageRepository;

    public KoerperfettAvarageQueryService(KoerperfettAvarageRepository koerperfettAvarageRepository) {
        this.koerperfettAvarageRepository = koerperfettAvarageRepository;
    }

    /**
     * Return a {@link List} of {@link KoerperfettAvarage} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KoerperfettAvarage> findByCriteria(KoerperfettAvarageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KoerperfettAvarage> specification = createSpecification(criteria);
        return koerperfettAvarageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KoerperfettAvarage} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KoerperfettAvarage> findByCriteria(KoerperfettAvarageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KoerperfettAvarage> specification = createSpecification(criteria);
        return koerperfettAvarageRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KoerperfettAvarageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KoerperfettAvarage> specification = createSpecification(criteria);
        return koerperfettAvarageRepository.count(specification);
    }

    /**
     * Function to convert {@link KoerperfettAvarageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KoerperfettAvarage> createSpecification(KoerperfettAvarageCriteria criteria) {
        Specification<KoerperfettAvarage> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KoerperfettAvarage_.id));
            }
            if (criteria.getGeschlecht() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGeschlecht(), KoerperfettAvarage_.geschlecht));
            }
            if (criteria.getAlter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAlter(), KoerperfettAvarage_.alter));
            }
            if (criteria.getKoerperfettanteil() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getKoerperfettanteil(), KoerperfettAvarage_.koerperfettanteil));
            }
            if (criteria.getDatumundZeit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatumundZeit(), KoerperfettAvarage_.datumundZeit));
            }
        }
        return specification;
    }
}
