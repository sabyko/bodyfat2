package de.projekt.bodyfat.web.rest;

import de.projekt.bodyfat.domain.KoerperfettAvarage;
import de.projekt.bodyfat.repository.KoerperfettAvarageRepository;
import de.projekt.bodyfat.service.KoerperfettAvarageQueryService;
import de.projekt.bodyfat.service.KoerperfettAvarageService;
import de.projekt.bodyfat.service.criteria.KoerperfettAvarageCriteria;
import de.projekt.bodyfat.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link de.projekt.bodyfat.domain.KoerperfettAvarage}.
 */
@RestController
@RequestMapping("/api")
public class KoerperfettAvarageResource {

    private final Logger log = LoggerFactory.getLogger(KoerperfettAvarageResource.class);

    private static final String ENTITY_NAME = "koerperfettAvarage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KoerperfettAvarageService koerperfettAvarageService;

    private final KoerperfettAvarageRepository koerperfettAvarageRepository;

    private final KoerperfettAvarageQueryService koerperfettAvarageQueryService;

    public KoerperfettAvarageResource(
        KoerperfettAvarageService koerperfettAvarageService,
        KoerperfettAvarageRepository koerperfettAvarageRepository,
        KoerperfettAvarageQueryService koerperfettAvarageQueryService
    ) {
        this.koerperfettAvarageService = koerperfettAvarageService;
        this.koerperfettAvarageRepository = koerperfettAvarageRepository;
        this.koerperfettAvarageQueryService = koerperfettAvarageQueryService;
    }

    /**
     * {@code POST  /koerperfett-avarages} : Create a new koerperfettAvarage.
     *
     * @param koerperfettAvarage the koerperfettAvarage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new koerperfettAvarage, or with status {@code 400 (Bad Request)} if the koerperfettAvarage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/koerperfett-avarages")
    public ResponseEntity<KoerperfettAvarage> createKoerperfettAvarage(@RequestBody KoerperfettAvarage koerperfettAvarage)
        throws URISyntaxException {
        log.debug("REST request to save KoerperfettAvarage : {}", koerperfettAvarage);
        if (koerperfettAvarage.getId() != null) {
            throw new BadRequestAlertException("A new koerperfettAvarage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KoerperfettAvarage result = koerperfettAvarageService.save(koerperfettAvarage);
        return ResponseEntity
            .created(new URI("/api/koerperfett-avarages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /koerperfett-avarages/:id} : Updates an existing koerperfettAvarage.
     *
     * @param id the id of the koerperfettAvarage to save.
     * @param koerperfettAvarage the koerperfettAvarage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koerperfettAvarage,
     * or with status {@code 400 (Bad Request)} if the koerperfettAvarage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the koerperfettAvarage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/koerperfett-avarages/{id}")
    public ResponseEntity<KoerperfettAvarage> updateKoerperfettAvarage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KoerperfettAvarage koerperfettAvarage
    ) throws URISyntaxException {
        log.debug("REST request to update KoerperfettAvarage : {}, {}", id, koerperfettAvarage);
        if (koerperfettAvarage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koerperfettAvarage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koerperfettAvarageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KoerperfettAvarage result = koerperfettAvarageService.update(koerperfettAvarage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, koerperfettAvarage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /koerperfett-avarages/:id} : Partial updates given fields of an existing koerperfettAvarage, field will ignore if it is null
     *
     * @param id the id of the koerperfettAvarage to save.
     * @param koerperfettAvarage the koerperfettAvarage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koerperfettAvarage,
     * or with status {@code 400 (Bad Request)} if the koerperfettAvarage is not valid,
     * or with status {@code 404 (Not Found)} if the koerperfettAvarage is not found,
     * or with status {@code 500 (Internal Server Error)} if the koerperfettAvarage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/koerperfett-avarages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KoerperfettAvarage> partialUpdateKoerperfettAvarage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KoerperfettAvarage koerperfettAvarage
    ) throws URISyntaxException {
        log.debug("REST request to partial update KoerperfettAvarage partially : {}, {}", id, koerperfettAvarage);
        if (koerperfettAvarage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koerperfettAvarage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koerperfettAvarageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KoerperfettAvarage> result = koerperfettAvarageService.partialUpdate(koerperfettAvarage);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, koerperfettAvarage.getId().toString())
        );
    }

    /**
     * {@code GET  /koerperfett-avarages} : get all the koerperfettAvarages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of koerperfettAvarages in body.
     */
    @GetMapping("/koerperfett-avarages")
    public ResponseEntity<List<KoerperfettAvarage>> getAllKoerperfettAvarages(
        KoerperfettAvarageCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KoerperfettAvarages by criteria: {}", criteria);
        Page<KoerperfettAvarage> page = koerperfettAvarageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /koerperfett-avarages/count} : count all the koerperfettAvarages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/koerperfett-avarages/count")
    public ResponseEntity<Long> countKoerperfettAvarages(KoerperfettAvarageCriteria criteria) {
        log.debug("REST request to count KoerperfettAvarages by criteria: {}", criteria);
        return ResponseEntity.ok().body(koerperfettAvarageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /koerperfett-avarages/:id} : get the "id" koerperfettAvarage.
     *
     * @param id the id of the koerperfettAvarage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the koerperfettAvarage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/koerperfett-avarages/{id}")
    public ResponseEntity<KoerperfettAvarage> getKoerperfettAvarage(@PathVariable Long id) {
        log.debug("REST request to get KoerperfettAvarage : {}", id);
        Optional<KoerperfettAvarage> koerperfettAvarage = koerperfettAvarageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(koerperfettAvarage);
    }

    /**
     * {@code DELETE  /koerperfett-avarages/:id} : delete the "id" koerperfettAvarage.
     *
     * @param id the id of the koerperfettAvarage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/koerperfett-avarages/{id}")
    public ResponseEntity<Void> deleteKoerperfettAvarage(@PathVariable Long id) {
        log.debug("REST request to delete KoerperfettAvarage : {}", id);
        koerperfettAvarageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
