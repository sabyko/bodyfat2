package de.projekt.bodyfat.web.rest;

import de.projekt.bodyfat.domain.Koerperfett;
import de.projekt.bodyfat.repository.KoerperfettRepository;
import de.projekt.bodyfat.service.KoerperfettQueryService;
import de.projekt.bodyfat.service.KoerperfettService;
import de.projekt.bodyfat.service.criteria.KoerperfettCriteria;
import de.projekt.bodyfat.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link de.projekt.bodyfat.domain.Koerperfett}.
 */
@RestController
@RequestMapping("/api")
public class KoerperfettResource {

    private final Logger log = LoggerFactory.getLogger(KoerperfettResource.class);

    private static final String ENTITY_NAME = "koerperfett";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KoerperfettService koerperfettService;

    private final KoerperfettRepository koerperfettRepository;

    private final KoerperfettQueryService koerperfettQueryService;

    public KoerperfettResource(
        KoerperfettService koerperfettService,
        KoerperfettRepository koerperfettRepository,
        KoerperfettQueryService koerperfettQueryService
    ) {
        this.koerperfettService = koerperfettService;
        this.koerperfettRepository = koerperfettRepository;
        this.koerperfettQueryService = koerperfettQueryService;
    }

    /**
     * {@code POST  /koerperfetts} : Create a new koerperfett.
     *
     * @param koerperfett the koerperfett to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new koerperfett, or with status {@code 400 (Bad Request)} if the koerperfett has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/koerperfetts")
    public ResponseEntity<Koerperfett> createKoerperfett(@Valid @RequestBody Koerperfett koerperfett) throws URISyntaxException {
        log.info("REST request to save Koerperfett : {}", koerperfett);
        System.out.println("");
        if (koerperfett.getId() != null) {
            throw new BadRequestAlertException("A new koerperfett cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Koerperfett updatedKoerperfett = koerperfettService.calculateKoerperfettanteil(koerperfett);
        Koerperfett result = koerperfettService.save(updatedKoerperfett);
        return ResponseEntity
            .created(new URI("/api/koerperfetts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /koerperfetts/:id} : Updates an existing koerperfett.
     *
     * @param id the id of the koerperfett to save.
     * @param koerperfett the koerperfett to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koerperfett,
     * or with status {@code 400 (Bad Request)} if the koerperfett is not valid,
     * or with status {@code 500 (Internal Server Error)} if the koerperfett couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/koerperfetts/{id}")
    public ResponseEntity<Koerperfett> updateKoerperfett(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Koerperfett koerperfett
    ) throws URISyntaxException {
        log.debug("REST request to update Koerperfett : {}, {}", id, koerperfett);
        if (koerperfett.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koerperfett.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koerperfettRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Koerperfett result = koerperfettService.update(koerperfett);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, koerperfett.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /koerperfetts/:id} : Partial updates given fields of an existing koerperfett, field will ignore if it is null
     *
     * @param id the id of the koerperfett to save.
     * @param koerperfett the koerperfett to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koerperfett,
     * or with status {@code 400 (Bad Request)} if the koerperfett is not valid,
     * or with status {@code 404 (Not Found)} if the koerperfett is not found,
     * or with status {@code 500 (Internal Server Error)} if the koerperfett couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/koerperfetts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Koerperfett> partialUpdateKoerperfett(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Koerperfett koerperfett
    ) throws URISyntaxException {
        log.debug("REST request to partial update Koerperfett partially : {}, {}", id, koerperfett);
        if (koerperfett.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koerperfett.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koerperfettRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Koerperfett> result = koerperfettService.partialUpdate(koerperfett);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, koerperfett.getId().toString())
        );
    }

    /**
     * {@code GET  /koerperfetts} : get all the koerperfetts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of koerperfetts in body.
     */
    @GetMapping("/koerperfetts")
    public ResponseEntity<List<Koerperfett>> getAllKoerperfetts(
        KoerperfettCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Koerperfetts by criteria: {}", criteria);
        Page<Koerperfett> page = koerperfettQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /koerperfetts/count} : count all the koerperfetts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/koerperfetts/count")
    public ResponseEntity<Long> countKoerperfetts(KoerperfettCriteria criteria) {
        log.debug("REST request to count Koerperfetts by criteria: {}", criteria);
        return ResponseEntity.ok().body(koerperfettQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /koerperfetts/:id} : get the "id" koerperfett.
     *
     * @param id the id of the koerperfett to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the koerperfett, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/koerperfetts/{id}")
    public ResponseEntity<Koerperfett> getKoerperfett(@PathVariable Long id) {
        log.debug("REST request to get Koerperfett : {}", id);
        Optional<Koerperfett> koerperfett = koerperfettService.findOne(id);
        return ResponseUtil.wrapOrNotFound(koerperfett);
    }

    /**
     * {@code DELETE  /koerperfetts/:id} : delete the "id" koerperfett.
     *
     * @param id the id of the koerperfett to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/koerperfetts/{id}")
    public ResponseEntity<Void> deleteKoerperfett(@PathVariable Long id) {
        log.debug("REST request to delete Koerperfett : {}", id);
        koerperfettService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
