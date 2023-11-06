package de.projekt.bodyfat.service;

import de.projekt.bodyfat.domain.*; // for static metamodels
import de.projekt.bodyfat.domain.Koerperfett;
import de.projekt.bodyfat.repository.KoerperfettRepository;
import de.projekt.bodyfat.service.criteria.KoerperfettCriteria;
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
 * Service for executing complex queries for {@link Koerperfett} entities in the database.
 * The main input is a {@link KoerperfettCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Koerperfett} or a {@link Page} of {@link Koerperfett} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KoerperfettQueryService extends QueryService<Koerperfett> {

    private final Logger log = LoggerFactory.getLogger(KoerperfettQueryService.class);

    private final KoerperfettRepository koerperfettRepository;

    public KoerperfettQueryService(KoerperfettRepository koerperfettRepository) {
        this.koerperfettRepository = koerperfettRepository;
    }

    /**
     * Return a {@link List} of {@link Koerperfett} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Koerperfett> findByCriteria(KoerperfettCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Koerperfett> specification = createSpecification(criteria);
        return koerperfettRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Koerperfett} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Koerperfett> findByCriteria(KoerperfettCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Koerperfett> specification = createSpecification(criteria);
        return koerperfettRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KoerperfettCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Koerperfett> specification = createSpecification(criteria);
        return koerperfettRepository.count(specification);
    }

    /**
     * Function to convert {@link KoerperfettCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Koerperfett> createSpecification(KoerperfettCriteria criteria) {
        Specification<Koerperfett> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Koerperfett_.id));
            }
            if (criteria.getPrivatoderfirma() != null) {
                specification = specification.and(buildSpecification(criteria.getPrivatoderfirma(), Koerperfett_.privatoderfirma));
            }
            if (criteria.getKoerpergroesse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKoerpergroesse(), Koerperfett_.koerpergroesse));
            }
            if (criteria.getNackenumfang() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNackenumfang(), Koerperfett_.nackenumfang));
            }
            if (criteria.getBauchumfang() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBauchumfang(), Koerperfett_.bauchumfang));
            }
            if (criteria.getHueftumfang() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHueftumfang(), Koerperfett_.hueftumfang));
            }
            if (criteria.getGeschlecht() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGeschlecht(), Koerperfett_.geschlecht));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Koerperfett_.age));
            }
            if (criteria.getKoerperfettanteil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKoerperfettanteil(), Koerperfett_.koerperfettanteil));
            }
            if (criteria.getDatumundZeit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatumundZeit(), Koerperfett_.datumundZeit));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Koerperfett_.url));
            }
            if (criteria.getSuccess() != null) {
                specification = specification.and(buildSpecification(criteria.getSuccess(), Koerperfett_.success));
            }
            if (criteria.getErrorMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorMessage(), Koerperfett_.errorMessage));
            }
        }
        return specification;
    }
}
