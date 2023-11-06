package de.projekt.bodyfat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.projekt.bodyfat.IntegrationTest;
import de.projekt.bodyfat.domain.KoerperfettAvarage;
import de.projekt.bodyfat.repository.KoerperfettAvarageRepository;
import de.projekt.bodyfat.service.criteria.KoerperfettAvarageCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KoerperfettAvarageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KoerperfettAvarageResourceIT {

    private static final String DEFAULT_GESCHLECHT = "AAAAAAAAAA";
    private static final String UPDATED_GESCHLECHT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALTER = 1;
    private static final Integer UPDATED_ALTER = 2;
    private static final Integer SMALLER_ALTER = 1 - 1;

    private static final Double DEFAULT_KOERPERFETTANTEIL = 1D;
    private static final Double UPDATED_KOERPERFETTANTEIL = 2D;
    private static final Double SMALLER_KOERPERFETTANTEIL = 1D - 1D;

    private static final Instant DEFAULT_DATUMUND_ZEIT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATUMUND_ZEIT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/koerperfett-avarages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KoerperfettAvarageRepository koerperfettAvarageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKoerperfettAvarageMockMvc;

    private KoerperfettAvarage koerperfettAvarage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KoerperfettAvarage createEntity(EntityManager em) {
        KoerperfettAvarage koerperfettAvarage = new KoerperfettAvarage()
            .geschlecht(DEFAULT_GESCHLECHT)
            .alter(DEFAULT_ALTER)
            .koerperfettanteil(DEFAULT_KOERPERFETTANTEIL)
            .datumundZeit(DEFAULT_DATUMUND_ZEIT);
        return koerperfettAvarage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KoerperfettAvarage createUpdatedEntity(EntityManager em) {
        KoerperfettAvarage koerperfettAvarage = new KoerperfettAvarage()
            .geschlecht(UPDATED_GESCHLECHT)
            .alter(UPDATED_ALTER)
            .koerperfettanteil(UPDATED_KOERPERFETTANTEIL)
            .datumundZeit(UPDATED_DATUMUND_ZEIT);
        return koerperfettAvarage;
    }

    @BeforeEach
    public void initTest() {
        koerperfettAvarage = createEntity(em);
    }

    @Test
    @Transactional
    void createKoerperfettAvarage() throws Exception {
        int databaseSizeBeforeCreate = koerperfettAvarageRepository.findAll().size();
        // Create the KoerperfettAvarage
        restKoerperfettAvarageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfettAvarage))
            )
            .andExpect(status().isCreated());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeCreate + 1);
        KoerperfettAvarage testKoerperfettAvarage = koerperfettAvarageList.get(koerperfettAvarageList.size() - 1);
        assertThat(testKoerperfettAvarage.getGeschlecht()).isEqualTo(DEFAULT_GESCHLECHT);
        assertThat(testKoerperfettAvarage.getAlter()).isEqualTo(DEFAULT_ALTER);
        assertThat(testKoerperfettAvarage.getKoerperfettanteil()).isEqualTo(DEFAULT_KOERPERFETTANTEIL);
        assertThat(testKoerperfettAvarage.getDatumundZeit()).isEqualTo(DEFAULT_DATUMUND_ZEIT);
    }

    @Test
    @Transactional
    void createKoerperfettAvarageWithExistingId() throws Exception {
        // Create the KoerperfettAvarage with an existing ID
        koerperfettAvarage.setId(1L);

        int databaseSizeBeforeCreate = koerperfettAvarageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKoerperfettAvarageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfettAvarage))
            )
            .andExpect(status().isBadRequest());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvarages() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList
        restKoerperfettAvarageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koerperfettAvarage.getId().intValue())))
            .andExpect(jsonPath("$.[*].geschlecht").value(hasItem(DEFAULT_GESCHLECHT)))
            .andExpect(jsonPath("$.[*].alter").value(hasItem(DEFAULT_ALTER)))
            .andExpect(jsonPath("$.[*].koerperfettanteil").value(hasItem(DEFAULT_KOERPERFETTANTEIL.doubleValue())))
            .andExpect(jsonPath("$.[*].datumundZeit").value(hasItem(DEFAULT_DATUMUND_ZEIT.toString())));
    }

    @Test
    @Transactional
    void getKoerperfettAvarage() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get the koerperfettAvarage
        restKoerperfettAvarageMockMvc
            .perform(get(ENTITY_API_URL_ID, koerperfettAvarage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(koerperfettAvarage.getId().intValue()))
            .andExpect(jsonPath("$.geschlecht").value(DEFAULT_GESCHLECHT))
            .andExpect(jsonPath("$.alter").value(DEFAULT_ALTER))
            .andExpect(jsonPath("$.koerperfettanteil").value(DEFAULT_KOERPERFETTANTEIL.doubleValue()))
            .andExpect(jsonPath("$.datumundZeit").value(DEFAULT_DATUMUND_ZEIT.toString()));
    }

    @Test
    @Transactional
    void getKoerperfettAvaragesByIdFiltering() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        Long id = koerperfettAvarage.getId();

        defaultKoerperfettAvarageShouldBeFound("id.equals=" + id);
        defaultKoerperfettAvarageShouldNotBeFound("id.notEquals=" + id);

        defaultKoerperfettAvarageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKoerperfettAvarageShouldNotBeFound("id.greaterThan=" + id);

        defaultKoerperfettAvarageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKoerperfettAvarageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByGeschlechtIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where geschlecht equals to DEFAULT_GESCHLECHT
        defaultKoerperfettAvarageShouldBeFound("geschlecht.equals=" + DEFAULT_GESCHLECHT);

        // Get all the koerperfettAvarageList where geschlecht equals to UPDATED_GESCHLECHT
        defaultKoerperfettAvarageShouldNotBeFound("geschlecht.equals=" + UPDATED_GESCHLECHT);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByGeschlechtIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where geschlecht in DEFAULT_GESCHLECHT or UPDATED_GESCHLECHT
        defaultKoerperfettAvarageShouldBeFound("geschlecht.in=" + DEFAULT_GESCHLECHT + "," + UPDATED_GESCHLECHT);

        // Get all the koerperfettAvarageList where geschlecht equals to UPDATED_GESCHLECHT
        defaultKoerperfettAvarageShouldNotBeFound("geschlecht.in=" + UPDATED_GESCHLECHT);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByGeschlechtIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where geschlecht is not null
        defaultKoerperfettAvarageShouldBeFound("geschlecht.specified=true");

        // Get all the koerperfettAvarageList where geschlecht is null
        defaultKoerperfettAvarageShouldNotBeFound("geschlecht.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByGeschlechtContainsSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where geschlecht contains DEFAULT_GESCHLECHT
        defaultKoerperfettAvarageShouldBeFound("geschlecht.contains=" + DEFAULT_GESCHLECHT);

        // Get all the koerperfettAvarageList where geschlecht contains UPDATED_GESCHLECHT
        defaultKoerperfettAvarageShouldNotBeFound("geschlecht.contains=" + UPDATED_GESCHLECHT);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByGeschlechtNotContainsSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where geschlecht does not contain DEFAULT_GESCHLECHT
        defaultKoerperfettAvarageShouldNotBeFound("geschlecht.doesNotContain=" + DEFAULT_GESCHLECHT);

        // Get all the koerperfettAvarageList where geschlecht does not contain UPDATED_GESCHLECHT
        defaultKoerperfettAvarageShouldBeFound("geschlecht.doesNotContain=" + UPDATED_GESCHLECHT);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByAlterIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where alter equals to DEFAULT_ALTER
        defaultKoerperfettAvarageShouldBeFound("alter.equals=" + DEFAULT_ALTER);

        // Get all the koerperfettAvarageList where alter equals to UPDATED_ALTER
        defaultKoerperfettAvarageShouldNotBeFound("alter.equals=" + UPDATED_ALTER);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByAlterIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where alter in DEFAULT_ALTER or UPDATED_ALTER
        defaultKoerperfettAvarageShouldBeFound("alter.in=" + DEFAULT_ALTER + "," + UPDATED_ALTER);

        // Get all the koerperfettAvarageList where alter equals to UPDATED_ALTER
        defaultKoerperfettAvarageShouldNotBeFound("alter.in=" + UPDATED_ALTER);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByAlterIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where alter is not null
        defaultKoerperfettAvarageShouldBeFound("alter.specified=true");

        // Get all the koerperfettAvarageList where alter is null
        defaultKoerperfettAvarageShouldNotBeFound("alter.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByAlterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where alter is greater than or equal to DEFAULT_ALTER
        defaultKoerperfettAvarageShouldBeFound("alter.greaterThanOrEqual=" + DEFAULT_ALTER);

        // Get all the koerperfettAvarageList where alter is greater than or equal to UPDATED_ALTER
        defaultKoerperfettAvarageShouldNotBeFound("alter.greaterThanOrEqual=" + UPDATED_ALTER);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByAlterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where alter is less than or equal to DEFAULT_ALTER
        defaultKoerperfettAvarageShouldBeFound("alter.lessThanOrEqual=" + DEFAULT_ALTER);

        // Get all the koerperfettAvarageList where alter is less than or equal to SMALLER_ALTER
        defaultKoerperfettAvarageShouldNotBeFound("alter.lessThanOrEqual=" + SMALLER_ALTER);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByAlterIsLessThanSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where alter is less than DEFAULT_ALTER
        defaultKoerperfettAvarageShouldNotBeFound("alter.lessThan=" + DEFAULT_ALTER);

        // Get all the koerperfettAvarageList where alter is less than UPDATED_ALTER
        defaultKoerperfettAvarageShouldBeFound("alter.lessThan=" + UPDATED_ALTER);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByAlterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where alter is greater than DEFAULT_ALTER
        defaultKoerperfettAvarageShouldNotBeFound("alter.greaterThan=" + DEFAULT_ALTER);

        // Get all the koerperfettAvarageList where alter is greater than SMALLER_ALTER
        defaultKoerperfettAvarageShouldBeFound("alter.greaterThan=" + SMALLER_ALTER);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByKoerperfettanteilIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where koerperfettanteil equals to DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldBeFound("koerperfettanteil.equals=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettAvarageList where koerperfettanteil equals to UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldNotBeFound("koerperfettanteil.equals=" + UPDATED_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByKoerperfettanteilIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where koerperfettanteil in DEFAULT_KOERPERFETTANTEIL or UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldBeFound("koerperfettanteil.in=" + DEFAULT_KOERPERFETTANTEIL + "," + UPDATED_KOERPERFETTANTEIL);

        // Get all the koerperfettAvarageList where koerperfettanteil equals to UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldNotBeFound("koerperfettanteil.in=" + UPDATED_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByKoerperfettanteilIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where koerperfettanteil is not null
        defaultKoerperfettAvarageShouldBeFound("koerperfettanteil.specified=true");

        // Get all the koerperfettAvarageList where koerperfettanteil is null
        defaultKoerperfettAvarageShouldNotBeFound("koerperfettanteil.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByKoerperfettanteilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where koerperfettanteil is greater than or equal to DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldBeFound("koerperfettanteil.greaterThanOrEqual=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettAvarageList where koerperfettanteil is greater than or equal to UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldNotBeFound("koerperfettanteil.greaterThanOrEqual=" + UPDATED_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByKoerperfettanteilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where koerperfettanteil is less than or equal to DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldBeFound("koerperfettanteil.lessThanOrEqual=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettAvarageList where koerperfettanteil is less than or equal to SMALLER_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldNotBeFound("koerperfettanteil.lessThanOrEqual=" + SMALLER_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByKoerperfettanteilIsLessThanSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where koerperfettanteil is less than DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldNotBeFound("koerperfettanteil.lessThan=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettAvarageList where koerperfettanteil is less than UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldBeFound("koerperfettanteil.lessThan=" + UPDATED_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByKoerperfettanteilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where koerperfettanteil is greater than DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldNotBeFound("koerperfettanteil.greaterThan=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettAvarageList where koerperfettanteil is greater than SMALLER_KOERPERFETTANTEIL
        defaultKoerperfettAvarageShouldBeFound("koerperfettanteil.greaterThan=" + SMALLER_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByDatumundZeitIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where datumundZeit equals to DEFAULT_DATUMUND_ZEIT
        defaultKoerperfettAvarageShouldBeFound("datumundZeit.equals=" + DEFAULT_DATUMUND_ZEIT);

        // Get all the koerperfettAvarageList where datumundZeit equals to UPDATED_DATUMUND_ZEIT
        defaultKoerperfettAvarageShouldNotBeFound("datumundZeit.equals=" + UPDATED_DATUMUND_ZEIT);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByDatumundZeitIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where datumundZeit in DEFAULT_DATUMUND_ZEIT or UPDATED_DATUMUND_ZEIT
        defaultKoerperfettAvarageShouldBeFound("datumundZeit.in=" + DEFAULT_DATUMUND_ZEIT + "," + UPDATED_DATUMUND_ZEIT);

        // Get all the koerperfettAvarageList where datumundZeit equals to UPDATED_DATUMUND_ZEIT
        defaultKoerperfettAvarageShouldNotBeFound("datumundZeit.in=" + UPDATED_DATUMUND_ZEIT);
    }

    @Test
    @Transactional
    void getAllKoerperfettAvaragesByDatumundZeitIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        // Get all the koerperfettAvarageList where datumundZeit is not null
        defaultKoerperfettAvarageShouldBeFound("datumundZeit.specified=true");

        // Get all the koerperfettAvarageList where datumundZeit is null
        defaultKoerperfettAvarageShouldNotBeFound("datumundZeit.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKoerperfettAvarageShouldBeFound(String filter) throws Exception {
        restKoerperfettAvarageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koerperfettAvarage.getId().intValue())))
            .andExpect(jsonPath("$.[*].geschlecht").value(hasItem(DEFAULT_GESCHLECHT)))
            .andExpect(jsonPath("$.[*].alter").value(hasItem(DEFAULT_ALTER)))
            .andExpect(jsonPath("$.[*].koerperfettanteil").value(hasItem(DEFAULT_KOERPERFETTANTEIL.doubleValue())))
            .andExpect(jsonPath("$.[*].datumundZeit").value(hasItem(DEFAULT_DATUMUND_ZEIT.toString())));

        // Check, that the count call also returns 1
        restKoerperfettAvarageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKoerperfettAvarageShouldNotBeFound(String filter) throws Exception {
        restKoerperfettAvarageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKoerperfettAvarageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKoerperfettAvarage() throws Exception {
        // Get the koerperfettAvarage
        restKoerperfettAvarageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKoerperfettAvarage() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();

        // Update the koerperfettAvarage
        KoerperfettAvarage updatedKoerperfettAvarage = koerperfettAvarageRepository.findById(koerperfettAvarage.getId()).get();
        // Disconnect from session so that the updates on updatedKoerperfettAvarage are not directly saved in db
        em.detach(updatedKoerperfettAvarage);
        updatedKoerperfettAvarage
            .geschlecht(UPDATED_GESCHLECHT)
            .alter(UPDATED_ALTER)
            .koerperfettanteil(UPDATED_KOERPERFETTANTEIL)
            .datumundZeit(UPDATED_DATUMUND_ZEIT);

        restKoerperfettAvarageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKoerperfettAvarage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKoerperfettAvarage))
            )
            .andExpect(status().isOk());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
        KoerperfettAvarage testKoerperfettAvarage = koerperfettAvarageList.get(koerperfettAvarageList.size() - 1);
        assertThat(testKoerperfettAvarage.getGeschlecht()).isEqualTo(UPDATED_GESCHLECHT);
        assertThat(testKoerperfettAvarage.getAlter()).isEqualTo(UPDATED_ALTER);
        assertThat(testKoerperfettAvarage.getKoerperfettanteil()).isEqualTo(UPDATED_KOERPERFETTANTEIL);
        assertThat(testKoerperfettAvarage.getDatumundZeit()).isEqualTo(UPDATED_DATUMUND_ZEIT);
    }

    @Test
    @Transactional
    void putNonExistingKoerperfettAvarage() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();
        koerperfettAvarage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoerperfettAvarageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, koerperfettAvarage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(koerperfettAvarage))
            )
            .andExpect(status().isBadRequest());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKoerperfettAvarage() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();
        koerperfettAvarage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoerperfettAvarageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(koerperfettAvarage))
            )
            .andExpect(status().isBadRequest());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKoerperfettAvarage() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();
        koerperfettAvarage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoerperfettAvarageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfettAvarage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKoerperfettAvarageWithPatch() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();

        // Update the koerperfettAvarage using partial update
        KoerperfettAvarage partialUpdatedKoerperfettAvarage = new KoerperfettAvarage();
        partialUpdatedKoerperfettAvarage.setId(koerperfettAvarage.getId());

        partialUpdatedKoerperfettAvarage
            .geschlecht(UPDATED_GESCHLECHT)
            .koerperfettanteil(UPDATED_KOERPERFETTANTEIL)
            .datumundZeit(UPDATED_DATUMUND_ZEIT);

        restKoerperfettAvarageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoerperfettAvarage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKoerperfettAvarage))
            )
            .andExpect(status().isOk());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
        KoerperfettAvarage testKoerperfettAvarage = koerperfettAvarageList.get(koerperfettAvarageList.size() - 1);
        assertThat(testKoerperfettAvarage.getGeschlecht()).isEqualTo(UPDATED_GESCHLECHT);
        assertThat(testKoerperfettAvarage.getAlter()).isEqualTo(DEFAULT_ALTER);
        assertThat(testKoerperfettAvarage.getKoerperfettanteil()).isEqualTo(UPDATED_KOERPERFETTANTEIL);
        assertThat(testKoerperfettAvarage.getDatumundZeit()).isEqualTo(UPDATED_DATUMUND_ZEIT);
    }

    @Test
    @Transactional
    void fullUpdateKoerperfettAvarageWithPatch() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();

        // Update the koerperfettAvarage using partial update
        KoerperfettAvarage partialUpdatedKoerperfettAvarage = new KoerperfettAvarage();
        partialUpdatedKoerperfettAvarage.setId(koerperfettAvarage.getId());

        partialUpdatedKoerperfettAvarage
            .geschlecht(UPDATED_GESCHLECHT)
            .alter(UPDATED_ALTER)
            .koerperfettanteil(UPDATED_KOERPERFETTANTEIL)
            .datumundZeit(UPDATED_DATUMUND_ZEIT);

        restKoerperfettAvarageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoerperfettAvarage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKoerperfettAvarage))
            )
            .andExpect(status().isOk());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
        KoerperfettAvarage testKoerperfettAvarage = koerperfettAvarageList.get(koerperfettAvarageList.size() - 1);
        assertThat(testKoerperfettAvarage.getGeschlecht()).isEqualTo(UPDATED_GESCHLECHT);
        assertThat(testKoerperfettAvarage.getAlter()).isEqualTo(UPDATED_ALTER);
        assertThat(testKoerperfettAvarage.getKoerperfettanteil()).isEqualTo(UPDATED_KOERPERFETTANTEIL);
        assertThat(testKoerperfettAvarage.getDatumundZeit()).isEqualTo(UPDATED_DATUMUND_ZEIT);
    }

    @Test
    @Transactional
    void patchNonExistingKoerperfettAvarage() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();
        koerperfettAvarage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoerperfettAvarageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, koerperfettAvarage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(koerperfettAvarage))
            )
            .andExpect(status().isBadRequest());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKoerperfettAvarage() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();
        koerperfettAvarage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoerperfettAvarageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(koerperfettAvarage))
            )
            .andExpect(status().isBadRequest());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKoerperfettAvarage() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettAvarageRepository.findAll().size();
        koerperfettAvarage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoerperfettAvarageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(koerperfettAvarage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KoerperfettAvarage in the database
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKoerperfettAvarage() throws Exception {
        // Initialize the database
        koerperfettAvarageRepository.saveAndFlush(koerperfettAvarage);

        int databaseSizeBeforeDelete = koerperfettAvarageRepository.findAll().size();

        // Delete the koerperfettAvarage
        restKoerperfettAvarageMockMvc
            .perform(delete(ENTITY_API_URL_ID, koerperfettAvarage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KoerperfettAvarage> koerperfettAvarageList = koerperfettAvarageRepository.findAll();
        assertThat(koerperfettAvarageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
