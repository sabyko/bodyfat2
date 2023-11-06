package de.projekt.bodyfat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.projekt.bodyfat.IntegrationTest;
import de.projekt.bodyfat.domain.Koerperfett;
import de.projekt.bodyfat.repository.KoerperfettRepository;
import de.projekt.bodyfat.service.criteria.KoerperfettCriteria;
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
 * Integration tests for the {@link KoerperfettResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KoerperfettResourceIT {

    private static final Boolean DEFAULT_PRIVATODERFIRMA = false;
    private static final Boolean UPDATED_PRIVATODERFIRMA = true;

    private static final Integer DEFAULT_KOERPERGROESSE = 1;
    private static final Integer UPDATED_KOERPERGROESSE = 2;
    private static final Integer SMALLER_KOERPERGROESSE = 1 - 1;

    private static final Integer DEFAULT_NACKENUMFANG = 1;
    private static final Integer UPDATED_NACKENUMFANG = 2;
    private static final Integer SMALLER_NACKENUMFANG = 1 - 1;

    private static final Integer DEFAULT_BAUCHUMFANG = 1;
    private static final Integer UPDATED_BAUCHUMFANG = 2;
    private static final Integer SMALLER_BAUCHUMFANG = 1 - 1;

    private static final Integer DEFAULT_HUEFTUMFANG = 1;
    private static final Integer UPDATED_HUEFTUMFANG = 2;
    private static final Integer SMALLER_HUEFTUMFANG = 1 - 1;

    private static final String DEFAULT_GESCHLECHT = "AAAAAAAAAA";
    private static final String UPDATED_GESCHLECHT = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final Double DEFAULT_KOERPERFETTANTEIL = 1D;
    private static final Double UPDATED_KOERPERFETTANTEIL = 2D;
    private static final Double SMALLER_KOERPERFETTANTEIL = 1D - 1D;

    private static final Instant DEFAULT_DATUMUND_ZEIT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATUMUND_ZEIT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final Boolean UPDATED_SUCCESS = true;

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/koerperfetts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KoerperfettRepository koerperfettRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKoerperfettMockMvc;

    private Koerperfett koerperfett;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koerperfett createEntity(EntityManager em) {
        Koerperfett koerperfett = new Koerperfett()
            .privatoderfirma(DEFAULT_PRIVATODERFIRMA)
            .koerpergroesse(DEFAULT_KOERPERGROESSE)
            .nackenumfang(DEFAULT_NACKENUMFANG)
            .bauchumfang(DEFAULT_BAUCHUMFANG)
            .hueftumfang(DEFAULT_HUEFTUMFANG)
            .geschlecht(DEFAULT_GESCHLECHT)
            .age(DEFAULT_AGE)
            .koerperfettanteil(DEFAULT_KOERPERFETTANTEIL)
            .datumundZeit(DEFAULT_DATUMUND_ZEIT)
            .url(DEFAULT_URL)
            .success(DEFAULT_SUCCESS)
            .errorMessage(DEFAULT_ERROR_MESSAGE);
        return koerperfett;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koerperfett createUpdatedEntity(EntityManager em) {
        Koerperfett koerperfett = new Koerperfett()
            .privatoderfirma(UPDATED_PRIVATODERFIRMA)
            .koerpergroesse(UPDATED_KOERPERGROESSE)
            .nackenumfang(UPDATED_NACKENUMFANG)
            .bauchumfang(UPDATED_BAUCHUMFANG)
            .hueftumfang(UPDATED_HUEFTUMFANG)
            .geschlecht(UPDATED_GESCHLECHT)
            .age(UPDATED_AGE)
            .koerperfettanteil(UPDATED_KOERPERFETTANTEIL)
            .datumundZeit(UPDATED_DATUMUND_ZEIT)
            .url(UPDATED_URL)
            .success(UPDATED_SUCCESS)
            .errorMessage(UPDATED_ERROR_MESSAGE);
        return koerperfett;
    }

    @BeforeEach
    public void initTest() {
        koerperfett = createEntity(em);
    }

    @Test
    @Transactional
    void createKoerperfett() throws Exception {
        int databaseSizeBeforeCreate = koerperfettRepository.findAll().size();
        // Create the Koerperfett
        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isCreated());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeCreate + 1);
        Koerperfett testKoerperfett = koerperfettList.get(koerperfettList.size() - 1);
        assertThat(testKoerperfett.getPrivatoderfirma()).isEqualTo(DEFAULT_PRIVATODERFIRMA);
        assertThat(testKoerperfett.getKoerpergroesse()).isEqualTo(DEFAULT_KOERPERGROESSE);
        assertThat(testKoerperfett.getNackenumfang()).isEqualTo(DEFAULT_NACKENUMFANG);
        assertThat(testKoerperfett.getBauchumfang()).isEqualTo(DEFAULT_BAUCHUMFANG);
        assertThat(testKoerperfett.getHueftumfang()).isEqualTo(DEFAULT_HUEFTUMFANG);
        assertThat(testKoerperfett.getGeschlecht()).isEqualTo(DEFAULT_GESCHLECHT);
        assertThat(testKoerperfett.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testKoerperfett.getKoerperfettanteil()).isEqualTo(DEFAULT_KOERPERFETTANTEIL);
        assertThat(testKoerperfett.getDatumundZeit()).isEqualTo(DEFAULT_DATUMUND_ZEIT);
        assertThat(testKoerperfett.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testKoerperfett.getSuccess()).isEqualTo(DEFAULT_SUCCESS);
        assertThat(testKoerperfett.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void createKoerperfettWithExistingId() throws Exception {
        // Create the Koerperfett with an existing ID
        koerperfett.setId(1L);

        int databaseSizeBeforeCreate = koerperfettRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isBadRequest());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrivatoderfirmaIsRequired() throws Exception {
        int databaseSizeBeforeTest = koerperfettRepository.findAll().size();
        // set the field null
        koerperfett.setPrivatoderfirma(null);

        // Create the Koerperfett, which fails.

        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isBadRequest());

        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKoerpergroesseIsRequired() throws Exception {
        int databaseSizeBeforeTest = koerperfettRepository.findAll().size();
        // set the field null
        koerperfett.setKoerpergroesse(null);

        // Create the Koerperfett, which fails.

        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isBadRequest());

        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNackenumfangIsRequired() throws Exception {
        int databaseSizeBeforeTest = koerperfettRepository.findAll().size();
        // set the field null
        koerperfett.setNackenumfang(null);

        // Create the Koerperfett, which fails.

        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isBadRequest());

        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBauchumfangIsRequired() throws Exception {
        int databaseSizeBeforeTest = koerperfettRepository.findAll().size();
        // set the field null
        koerperfett.setBauchumfang(null);

        // Create the Koerperfett, which fails.

        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isBadRequest());

        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGeschlechtIsRequired() throws Exception {
        int databaseSizeBeforeTest = koerperfettRepository.findAll().size();
        // set the field null
        koerperfett.setGeschlecht(null);

        // Create the Koerperfett, which fails.

        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isBadRequest());

        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = koerperfettRepository.findAll().size();
        // set the field null
        koerperfett.setAge(null);

        // Create the Koerperfett, which fails.

        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isBadRequest());

        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKoerperfetts() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList
        restKoerperfettMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koerperfett.getId().intValue())))
            .andExpect(jsonPath("$.[*].privatoderfirma").value(hasItem(DEFAULT_PRIVATODERFIRMA.booleanValue())))
            .andExpect(jsonPath("$.[*].koerpergroesse").value(hasItem(DEFAULT_KOERPERGROESSE)))
            .andExpect(jsonPath("$.[*].nackenumfang").value(hasItem(DEFAULT_NACKENUMFANG)))
            .andExpect(jsonPath("$.[*].bauchumfang").value(hasItem(DEFAULT_BAUCHUMFANG)))
            .andExpect(jsonPath("$.[*].hueftumfang").value(hasItem(DEFAULT_HUEFTUMFANG)))
            .andExpect(jsonPath("$.[*].geschlecht").value(hasItem(DEFAULT_GESCHLECHT)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].koerperfettanteil").value(hasItem(DEFAULT_KOERPERFETTANTEIL.doubleValue())))
            .andExpect(jsonPath("$.[*].datumundZeit").value(hasItem(DEFAULT_DATUMUND_ZEIT.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].success").value(hasItem(DEFAULT_SUCCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)));
    }

    @Test
    @Transactional
    void getKoerperfett() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get the koerperfett
        restKoerperfettMockMvc
            .perform(get(ENTITY_API_URL_ID, koerperfett.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(koerperfett.getId().intValue()))
            .andExpect(jsonPath("$.privatoderfirma").value(DEFAULT_PRIVATODERFIRMA.booleanValue()))
            .andExpect(jsonPath("$.koerpergroesse").value(DEFAULT_KOERPERGROESSE))
            .andExpect(jsonPath("$.nackenumfang").value(DEFAULT_NACKENUMFANG))
            .andExpect(jsonPath("$.bauchumfang").value(DEFAULT_BAUCHUMFANG))
            .andExpect(jsonPath("$.hueftumfang").value(DEFAULT_HUEFTUMFANG))
            .andExpect(jsonPath("$.geschlecht").value(DEFAULT_GESCHLECHT))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.koerperfettanteil").value(DEFAULT_KOERPERFETTANTEIL.doubleValue()))
            .andExpect(jsonPath("$.datumundZeit").value(DEFAULT_DATUMUND_ZEIT.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.success").value(DEFAULT_SUCCESS.booleanValue()))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE));
    }

    @Test
    @Transactional
    void getKoerperfettsByIdFiltering() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        Long id = koerperfett.getId();

        defaultKoerperfettShouldBeFound("id.equals=" + id);
        defaultKoerperfettShouldNotBeFound("id.notEquals=" + id);

        defaultKoerperfettShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKoerperfettShouldNotBeFound("id.greaterThan=" + id);

        defaultKoerperfettShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKoerperfettShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByPrivatoderfirmaIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where privatoderfirma equals to DEFAULT_PRIVATODERFIRMA
        defaultKoerperfettShouldBeFound("privatoderfirma.equals=" + DEFAULT_PRIVATODERFIRMA);

        // Get all the koerperfettList where privatoderfirma equals to UPDATED_PRIVATODERFIRMA
        defaultKoerperfettShouldNotBeFound("privatoderfirma.equals=" + UPDATED_PRIVATODERFIRMA);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByPrivatoderfirmaIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where privatoderfirma in DEFAULT_PRIVATODERFIRMA or UPDATED_PRIVATODERFIRMA
        defaultKoerperfettShouldBeFound("privatoderfirma.in=" + DEFAULT_PRIVATODERFIRMA + "," + UPDATED_PRIVATODERFIRMA);

        // Get all the koerperfettList where privatoderfirma equals to UPDATED_PRIVATODERFIRMA
        defaultKoerperfettShouldNotBeFound("privatoderfirma.in=" + UPDATED_PRIVATODERFIRMA);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByPrivatoderfirmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where privatoderfirma is not null
        defaultKoerperfettShouldBeFound("privatoderfirma.specified=true");

        // Get all the koerperfettList where privatoderfirma is null
        defaultKoerperfettShouldNotBeFound("privatoderfirma.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerpergroesseIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerpergroesse equals to DEFAULT_KOERPERGROESSE
        defaultKoerperfettShouldBeFound("koerpergroesse.equals=" + DEFAULT_KOERPERGROESSE);

        // Get all the koerperfettList where koerpergroesse equals to UPDATED_KOERPERGROESSE
        defaultKoerperfettShouldNotBeFound("koerpergroesse.equals=" + UPDATED_KOERPERGROESSE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerpergroesseIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerpergroesse in DEFAULT_KOERPERGROESSE or UPDATED_KOERPERGROESSE
        defaultKoerperfettShouldBeFound("koerpergroesse.in=" + DEFAULT_KOERPERGROESSE + "," + UPDATED_KOERPERGROESSE);

        // Get all the koerperfettList where koerpergroesse equals to UPDATED_KOERPERGROESSE
        defaultKoerperfettShouldNotBeFound("koerpergroesse.in=" + UPDATED_KOERPERGROESSE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerpergroesseIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerpergroesse is not null
        defaultKoerperfettShouldBeFound("koerpergroesse.specified=true");

        // Get all the koerperfettList where koerpergroesse is null
        defaultKoerperfettShouldNotBeFound("koerpergroesse.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerpergroesseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerpergroesse is greater than or equal to DEFAULT_KOERPERGROESSE
        defaultKoerperfettShouldBeFound("koerpergroesse.greaterThanOrEqual=" + DEFAULT_KOERPERGROESSE);

        // Get all the koerperfettList where koerpergroesse is greater than or equal to UPDATED_KOERPERGROESSE
        defaultKoerperfettShouldNotBeFound("koerpergroesse.greaterThanOrEqual=" + UPDATED_KOERPERGROESSE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerpergroesseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerpergroesse is less than or equal to DEFAULT_KOERPERGROESSE
        defaultKoerperfettShouldBeFound("koerpergroesse.lessThanOrEqual=" + DEFAULT_KOERPERGROESSE);

        // Get all the koerperfettList where koerpergroesse is less than or equal to SMALLER_KOERPERGROESSE
        defaultKoerperfettShouldNotBeFound("koerpergroesse.lessThanOrEqual=" + SMALLER_KOERPERGROESSE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerpergroesseIsLessThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerpergroesse is less than DEFAULT_KOERPERGROESSE
        defaultKoerperfettShouldNotBeFound("koerpergroesse.lessThan=" + DEFAULT_KOERPERGROESSE);

        // Get all the koerperfettList where koerpergroesse is less than UPDATED_KOERPERGROESSE
        defaultKoerperfettShouldBeFound("koerpergroesse.lessThan=" + UPDATED_KOERPERGROESSE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerpergroesseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerpergroesse is greater than DEFAULT_KOERPERGROESSE
        defaultKoerperfettShouldNotBeFound("koerpergroesse.greaterThan=" + DEFAULT_KOERPERGROESSE);

        // Get all the koerperfettList where koerpergroesse is greater than SMALLER_KOERPERGROESSE
        defaultKoerperfettShouldBeFound("koerpergroesse.greaterThan=" + SMALLER_KOERPERGROESSE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByNackenumfangIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where nackenumfang equals to DEFAULT_NACKENUMFANG
        defaultKoerperfettShouldBeFound("nackenumfang.equals=" + DEFAULT_NACKENUMFANG);

        // Get all the koerperfettList where nackenumfang equals to UPDATED_NACKENUMFANG
        defaultKoerperfettShouldNotBeFound("nackenumfang.equals=" + UPDATED_NACKENUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByNackenumfangIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where nackenumfang in DEFAULT_NACKENUMFANG or UPDATED_NACKENUMFANG
        defaultKoerperfettShouldBeFound("nackenumfang.in=" + DEFAULT_NACKENUMFANG + "," + UPDATED_NACKENUMFANG);

        // Get all the koerperfettList where nackenumfang equals to UPDATED_NACKENUMFANG
        defaultKoerperfettShouldNotBeFound("nackenumfang.in=" + UPDATED_NACKENUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByNackenumfangIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where nackenumfang is not null
        defaultKoerperfettShouldBeFound("nackenumfang.specified=true");

        // Get all the koerperfettList where nackenumfang is null
        defaultKoerperfettShouldNotBeFound("nackenumfang.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByNackenumfangIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where nackenumfang is greater than or equal to DEFAULT_NACKENUMFANG
        defaultKoerperfettShouldBeFound("nackenumfang.greaterThanOrEqual=" + DEFAULT_NACKENUMFANG);

        // Get all the koerperfettList where nackenumfang is greater than or equal to UPDATED_NACKENUMFANG
        defaultKoerperfettShouldNotBeFound("nackenumfang.greaterThanOrEqual=" + UPDATED_NACKENUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByNackenumfangIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where nackenumfang is less than or equal to DEFAULT_NACKENUMFANG
        defaultKoerperfettShouldBeFound("nackenumfang.lessThanOrEqual=" + DEFAULT_NACKENUMFANG);

        // Get all the koerperfettList where nackenumfang is less than or equal to SMALLER_NACKENUMFANG
        defaultKoerperfettShouldNotBeFound("nackenumfang.lessThanOrEqual=" + SMALLER_NACKENUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByNackenumfangIsLessThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where nackenumfang is less than DEFAULT_NACKENUMFANG
        defaultKoerperfettShouldNotBeFound("nackenumfang.lessThan=" + DEFAULT_NACKENUMFANG);

        // Get all the koerperfettList where nackenumfang is less than UPDATED_NACKENUMFANG
        defaultKoerperfettShouldBeFound("nackenumfang.lessThan=" + UPDATED_NACKENUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByNackenumfangIsGreaterThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where nackenumfang is greater than DEFAULT_NACKENUMFANG
        defaultKoerperfettShouldNotBeFound("nackenumfang.greaterThan=" + DEFAULT_NACKENUMFANG);

        // Get all the koerperfettList where nackenumfang is greater than SMALLER_NACKENUMFANG
        defaultKoerperfettShouldBeFound("nackenumfang.greaterThan=" + SMALLER_NACKENUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByBauchumfangIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where bauchumfang equals to DEFAULT_BAUCHUMFANG
        defaultKoerperfettShouldBeFound("bauchumfang.equals=" + DEFAULT_BAUCHUMFANG);

        // Get all the koerperfettList where bauchumfang equals to UPDATED_BAUCHUMFANG
        defaultKoerperfettShouldNotBeFound("bauchumfang.equals=" + UPDATED_BAUCHUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByBauchumfangIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where bauchumfang in DEFAULT_BAUCHUMFANG or UPDATED_BAUCHUMFANG
        defaultKoerperfettShouldBeFound("bauchumfang.in=" + DEFAULT_BAUCHUMFANG + "," + UPDATED_BAUCHUMFANG);

        // Get all the koerperfettList where bauchumfang equals to UPDATED_BAUCHUMFANG
        defaultKoerperfettShouldNotBeFound("bauchumfang.in=" + UPDATED_BAUCHUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByBauchumfangIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where bauchumfang is not null
        defaultKoerperfettShouldBeFound("bauchumfang.specified=true");

        // Get all the koerperfettList where bauchumfang is null
        defaultKoerperfettShouldNotBeFound("bauchumfang.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByBauchumfangIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where bauchumfang is greater than or equal to DEFAULT_BAUCHUMFANG
        defaultKoerperfettShouldBeFound("bauchumfang.greaterThanOrEqual=" + DEFAULT_BAUCHUMFANG);

        // Get all the koerperfettList where bauchumfang is greater than or equal to UPDATED_BAUCHUMFANG
        defaultKoerperfettShouldNotBeFound("bauchumfang.greaterThanOrEqual=" + UPDATED_BAUCHUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByBauchumfangIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where bauchumfang is less than or equal to DEFAULT_BAUCHUMFANG
        defaultKoerperfettShouldBeFound("bauchumfang.lessThanOrEqual=" + DEFAULT_BAUCHUMFANG);

        // Get all the koerperfettList where bauchumfang is less than or equal to SMALLER_BAUCHUMFANG
        defaultKoerperfettShouldNotBeFound("bauchumfang.lessThanOrEqual=" + SMALLER_BAUCHUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByBauchumfangIsLessThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where bauchumfang is less than DEFAULT_BAUCHUMFANG
        defaultKoerperfettShouldNotBeFound("bauchumfang.lessThan=" + DEFAULT_BAUCHUMFANG);

        // Get all the koerperfettList where bauchumfang is less than UPDATED_BAUCHUMFANG
        defaultKoerperfettShouldBeFound("bauchumfang.lessThan=" + UPDATED_BAUCHUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByBauchumfangIsGreaterThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where bauchumfang is greater than DEFAULT_BAUCHUMFANG
        defaultKoerperfettShouldNotBeFound("bauchumfang.greaterThan=" + DEFAULT_BAUCHUMFANG);

        // Get all the koerperfettList where bauchumfang is greater than SMALLER_BAUCHUMFANG
        defaultKoerperfettShouldBeFound("bauchumfang.greaterThan=" + SMALLER_BAUCHUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByHueftumfangIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where hueftumfang equals to DEFAULT_HUEFTUMFANG
        defaultKoerperfettShouldBeFound("hueftumfang.equals=" + DEFAULT_HUEFTUMFANG);

        // Get all the koerperfettList where hueftumfang equals to UPDATED_HUEFTUMFANG
        defaultKoerperfettShouldNotBeFound("hueftumfang.equals=" + UPDATED_HUEFTUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByHueftumfangIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where hueftumfang in DEFAULT_HUEFTUMFANG or UPDATED_HUEFTUMFANG
        defaultKoerperfettShouldBeFound("hueftumfang.in=" + DEFAULT_HUEFTUMFANG + "," + UPDATED_HUEFTUMFANG);

        // Get all the koerperfettList where hueftumfang equals to UPDATED_HUEFTUMFANG
        defaultKoerperfettShouldNotBeFound("hueftumfang.in=" + UPDATED_HUEFTUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByHueftumfangIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where hueftumfang is not null
        defaultKoerperfettShouldBeFound("hueftumfang.specified=true");

        // Get all the koerperfettList where hueftumfang is null
        defaultKoerperfettShouldNotBeFound("hueftumfang.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByHueftumfangIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where hueftumfang is greater than or equal to DEFAULT_HUEFTUMFANG
        defaultKoerperfettShouldBeFound("hueftumfang.greaterThanOrEqual=" + DEFAULT_HUEFTUMFANG);

        // Get all the koerperfettList where hueftumfang is greater than or equal to UPDATED_HUEFTUMFANG
        defaultKoerperfettShouldNotBeFound("hueftumfang.greaterThanOrEqual=" + UPDATED_HUEFTUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByHueftumfangIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where hueftumfang is less than or equal to DEFAULT_HUEFTUMFANG
        defaultKoerperfettShouldBeFound("hueftumfang.lessThanOrEqual=" + DEFAULT_HUEFTUMFANG);

        // Get all the koerperfettList where hueftumfang is less than or equal to SMALLER_HUEFTUMFANG
        defaultKoerperfettShouldNotBeFound("hueftumfang.lessThanOrEqual=" + SMALLER_HUEFTUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByHueftumfangIsLessThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where hueftumfang is less than DEFAULT_HUEFTUMFANG
        defaultKoerperfettShouldNotBeFound("hueftumfang.lessThan=" + DEFAULT_HUEFTUMFANG);

        // Get all the koerperfettList where hueftumfang is less than UPDATED_HUEFTUMFANG
        defaultKoerperfettShouldBeFound("hueftumfang.lessThan=" + UPDATED_HUEFTUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByHueftumfangIsGreaterThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where hueftumfang is greater than DEFAULT_HUEFTUMFANG
        defaultKoerperfettShouldNotBeFound("hueftumfang.greaterThan=" + DEFAULT_HUEFTUMFANG);

        // Get all the koerperfettList where hueftumfang is greater than SMALLER_HUEFTUMFANG
        defaultKoerperfettShouldBeFound("hueftumfang.greaterThan=" + SMALLER_HUEFTUMFANG);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByGeschlechtIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where geschlecht equals to DEFAULT_GESCHLECHT
        defaultKoerperfettShouldBeFound("geschlecht.equals=" + DEFAULT_GESCHLECHT);

        // Get all the koerperfettList where geschlecht equals to UPDATED_GESCHLECHT
        defaultKoerperfettShouldNotBeFound("geschlecht.equals=" + UPDATED_GESCHLECHT);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByGeschlechtIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where geschlecht in DEFAULT_GESCHLECHT or UPDATED_GESCHLECHT
        defaultKoerperfettShouldBeFound("geschlecht.in=" + DEFAULT_GESCHLECHT + "," + UPDATED_GESCHLECHT);

        // Get all the koerperfettList where geschlecht equals to UPDATED_GESCHLECHT
        defaultKoerperfettShouldNotBeFound("geschlecht.in=" + UPDATED_GESCHLECHT);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByGeschlechtIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where geschlecht is not null
        defaultKoerperfettShouldBeFound("geschlecht.specified=true");

        // Get all the koerperfettList where geschlecht is null
        defaultKoerperfettShouldNotBeFound("geschlecht.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByGeschlechtContainsSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where geschlecht contains DEFAULT_GESCHLECHT
        defaultKoerperfettShouldBeFound("geschlecht.contains=" + DEFAULT_GESCHLECHT);

        // Get all the koerperfettList where geschlecht contains UPDATED_GESCHLECHT
        defaultKoerperfettShouldNotBeFound("geschlecht.contains=" + UPDATED_GESCHLECHT);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByGeschlechtNotContainsSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where geschlecht does not contain DEFAULT_GESCHLECHT
        defaultKoerperfettShouldNotBeFound("geschlecht.doesNotContain=" + DEFAULT_GESCHLECHT);

        // Get all the koerperfettList where geschlecht does not contain UPDATED_GESCHLECHT
        defaultKoerperfettShouldBeFound("geschlecht.doesNotContain=" + UPDATED_GESCHLECHT);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where age equals to DEFAULT_AGE
        defaultKoerperfettShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the koerperfettList where age equals to UPDATED_AGE
        defaultKoerperfettShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where age in DEFAULT_AGE or UPDATED_AGE
        defaultKoerperfettShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the koerperfettList where age equals to UPDATED_AGE
        defaultKoerperfettShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where age is not null
        defaultKoerperfettShouldBeFound("age.specified=true");

        // Get all the koerperfettList where age is null
        defaultKoerperfettShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where age is greater than or equal to DEFAULT_AGE
        defaultKoerperfettShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the koerperfettList where age is greater than or equal to UPDATED_AGE
        defaultKoerperfettShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where age is less than or equal to DEFAULT_AGE
        defaultKoerperfettShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the koerperfettList where age is less than or equal to SMALLER_AGE
        defaultKoerperfettShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where age is less than DEFAULT_AGE
        defaultKoerperfettShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the koerperfettList where age is less than UPDATED_AGE
        defaultKoerperfettShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where age is greater than DEFAULT_AGE
        defaultKoerperfettShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the koerperfettList where age is greater than SMALLER_AGE
        defaultKoerperfettShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerperfettanteilIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerperfettanteil equals to DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettShouldBeFound("koerperfettanteil.equals=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettList where koerperfettanteil equals to UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettShouldNotBeFound("koerperfettanteil.equals=" + UPDATED_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerperfettanteilIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerperfettanteil in DEFAULT_KOERPERFETTANTEIL or UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettShouldBeFound("koerperfettanteil.in=" + DEFAULT_KOERPERFETTANTEIL + "," + UPDATED_KOERPERFETTANTEIL);

        // Get all the koerperfettList where koerperfettanteil equals to UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettShouldNotBeFound("koerperfettanteil.in=" + UPDATED_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerperfettanteilIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerperfettanteil is not null
        defaultKoerperfettShouldBeFound("koerperfettanteil.specified=true");

        // Get all the koerperfettList where koerperfettanteil is null
        defaultKoerperfettShouldNotBeFound("koerperfettanteil.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerperfettanteilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerperfettanteil is greater than or equal to DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettShouldBeFound("koerperfettanteil.greaterThanOrEqual=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettList where koerperfettanteil is greater than or equal to UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettShouldNotBeFound("koerperfettanteil.greaterThanOrEqual=" + UPDATED_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerperfettanteilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerperfettanteil is less than or equal to DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettShouldBeFound("koerperfettanteil.lessThanOrEqual=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettList where koerperfettanteil is less than or equal to SMALLER_KOERPERFETTANTEIL
        defaultKoerperfettShouldNotBeFound("koerperfettanteil.lessThanOrEqual=" + SMALLER_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerperfettanteilIsLessThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerperfettanteil is less than DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettShouldNotBeFound("koerperfettanteil.lessThan=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettList where koerperfettanteil is less than UPDATED_KOERPERFETTANTEIL
        defaultKoerperfettShouldBeFound("koerperfettanteil.lessThan=" + UPDATED_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByKoerperfettanteilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where koerperfettanteil is greater than DEFAULT_KOERPERFETTANTEIL
        defaultKoerperfettShouldNotBeFound("koerperfettanteil.greaterThan=" + DEFAULT_KOERPERFETTANTEIL);

        // Get all the koerperfettList where koerperfettanteil is greater than SMALLER_KOERPERFETTANTEIL
        defaultKoerperfettShouldBeFound("koerperfettanteil.greaterThan=" + SMALLER_KOERPERFETTANTEIL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByDatumundZeitIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where datumundZeit equals to DEFAULT_DATUMUND_ZEIT
        defaultKoerperfettShouldBeFound("datumundZeit.equals=" + DEFAULT_DATUMUND_ZEIT);

        // Get all the koerperfettList where datumundZeit equals to UPDATED_DATUMUND_ZEIT
        defaultKoerperfettShouldNotBeFound("datumundZeit.equals=" + UPDATED_DATUMUND_ZEIT);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByDatumundZeitIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where datumundZeit in DEFAULT_DATUMUND_ZEIT or UPDATED_DATUMUND_ZEIT
        defaultKoerperfettShouldBeFound("datumundZeit.in=" + DEFAULT_DATUMUND_ZEIT + "," + UPDATED_DATUMUND_ZEIT);

        // Get all the koerperfettList where datumundZeit equals to UPDATED_DATUMUND_ZEIT
        defaultKoerperfettShouldNotBeFound("datumundZeit.in=" + UPDATED_DATUMUND_ZEIT);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByDatumundZeitIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where datumundZeit is not null
        defaultKoerperfettShouldBeFound("datumundZeit.specified=true");

        // Get all the koerperfettList where datumundZeit is null
        defaultKoerperfettShouldNotBeFound("datumundZeit.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where url equals to DEFAULT_URL
        defaultKoerperfettShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the koerperfettList where url equals to UPDATED_URL
        defaultKoerperfettShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where url in DEFAULT_URL or UPDATED_URL
        defaultKoerperfettShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the koerperfettList where url equals to UPDATED_URL
        defaultKoerperfettShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where url is not null
        defaultKoerperfettShouldBeFound("url.specified=true");

        // Get all the koerperfettList where url is null
        defaultKoerperfettShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByUrlContainsSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where url contains DEFAULT_URL
        defaultKoerperfettShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the koerperfettList where url contains UPDATED_URL
        defaultKoerperfettShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where url does not contain DEFAULT_URL
        defaultKoerperfettShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the koerperfettList where url does not contain UPDATED_URL
        defaultKoerperfettShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllKoerperfettsBySuccessIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where success equals to DEFAULT_SUCCESS
        defaultKoerperfettShouldBeFound("success.equals=" + DEFAULT_SUCCESS);

        // Get all the koerperfettList where success equals to UPDATED_SUCCESS
        defaultKoerperfettShouldNotBeFound("success.equals=" + UPDATED_SUCCESS);
    }

    @Test
    @Transactional
    void getAllKoerperfettsBySuccessIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where success in DEFAULT_SUCCESS or UPDATED_SUCCESS
        defaultKoerperfettShouldBeFound("success.in=" + DEFAULT_SUCCESS + "," + UPDATED_SUCCESS);

        // Get all the koerperfettList where success equals to UPDATED_SUCCESS
        defaultKoerperfettShouldNotBeFound("success.in=" + UPDATED_SUCCESS);
    }

    @Test
    @Transactional
    void getAllKoerperfettsBySuccessIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where success is not null
        defaultKoerperfettShouldBeFound("success.specified=true");

        // Get all the koerperfettList where success is null
        defaultKoerperfettShouldNotBeFound("success.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByErrorMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where errorMessage equals to DEFAULT_ERROR_MESSAGE
        defaultKoerperfettShouldBeFound("errorMessage.equals=" + DEFAULT_ERROR_MESSAGE);

        // Get all the koerperfettList where errorMessage equals to UPDATED_ERROR_MESSAGE
        defaultKoerperfettShouldNotBeFound("errorMessage.equals=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByErrorMessageIsInShouldWork() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where errorMessage in DEFAULT_ERROR_MESSAGE or UPDATED_ERROR_MESSAGE
        defaultKoerperfettShouldBeFound("errorMessage.in=" + DEFAULT_ERROR_MESSAGE + "," + UPDATED_ERROR_MESSAGE);

        // Get all the koerperfettList where errorMessage equals to UPDATED_ERROR_MESSAGE
        defaultKoerperfettShouldNotBeFound("errorMessage.in=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByErrorMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where errorMessage is not null
        defaultKoerperfettShouldBeFound("errorMessage.specified=true");

        // Get all the koerperfettList where errorMessage is null
        defaultKoerperfettShouldNotBeFound("errorMessage.specified=false");
    }

    @Test
    @Transactional
    void getAllKoerperfettsByErrorMessageContainsSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where errorMessage contains DEFAULT_ERROR_MESSAGE
        defaultKoerperfettShouldBeFound("errorMessage.contains=" + DEFAULT_ERROR_MESSAGE);

        // Get all the koerperfettList where errorMessage contains UPDATED_ERROR_MESSAGE
        defaultKoerperfettShouldNotBeFound("errorMessage.contains=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllKoerperfettsByErrorMessageNotContainsSomething() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList where errorMessage does not contain DEFAULT_ERROR_MESSAGE
        defaultKoerperfettShouldNotBeFound("errorMessage.doesNotContain=" + DEFAULT_ERROR_MESSAGE);

        // Get all the koerperfettList where errorMessage does not contain UPDATED_ERROR_MESSAGE
        defaultKoerperfettShouldBeFound("errorMessage.doesNotContain=" + UPDATED_ERROR_MESSAGE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKoerperfettShouldBeFound(String filter) throws Exception {
        restKoerperfettMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koerperfett.getId().intValue())))
            .andExpect(jsonPath("$.[*].privatoderfirma").value(hasItem(DEFAULT_PRIVATODERFIRMA.booleanValue())))
            .andExpect(jsonPath("$.[*].koerpergroesse").value(hasItem(DEFAULT_KOERPERGROESSE)))
            .andExpect(jsonPath("$.[*].nackenumfang").value(hasItem(DEFAULT_NACKENUMFANG)))
            .andExpect(jsonPath("$.[*].bauchumfang").value(hasItem(DEFAULT_BAUCHUMFANG)))
            .andExpect(jsonPath("$.[*].hueftumfang").value(hasItem(DEFAULT_HUEFTUMFANG)))
            .andExpect(jsonPath("$.[*].geschlecht").value(hasItem(DEFAULT_GESCHLECHT)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].koerperfettanteil").value(hasItem(DEFAULT_KOERPERFETTANTEIL.doubleValue())))
            .andExpect(jsonPath("$.[*].datumundZeit").value(hasItem(DEFAULT_DATUMUND_ZEIT.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].success").value(hasItem(DEFAULT_SUCCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)));

        // Check, that the count call also returns 1
        restKoerperfettMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKoerperfettShouldNotBeFound(String filter) throws Exception {
        restKoerperfettMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKoerperfettMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKoerperfett() throws Exception {
        // Get the koerperfett
        restKoerperfettMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKoerperfett() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();

        // Update the koerperfett
        Koerperfett updatedKoerperfett = koerperfettRepository.findById(koerperfett.getId()).get();
        // Disconnect from session so that the updates on updatedKoerperfett are not directly saved in db
        em.detach(updatedKoerperfett);
        updatedKoerperfett
            .privatoderfirma(UPDATED_PRIVATODERFIRMA)
            .koerpergroesse(UPDATED_KOERPERGROESSE)
            .nackenumfang(UPDATED_NACKENUMFANG)
            .bauchumfang(UPDATED_BAUCHUMFANG)
            .hueftumfang(UPDATED_HUEFTUMFANG)
            .geschlecht(UPDATED_GESCHLECHT)
            .age(UPDATED_AGE)
            .koerperfettanteil(UPDATED_KOERPERFETTANTEIL)
            .datumundZeit(UPDATED_DATUMUND_ZEIT)
            .url(UPDATED_URL)
            .success(UPDATED_SUCCESS)
            .errorMessage(UPDATED_ERROR_MESSAGE);

        restKoerperfettMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKoerperfett.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKoerperfett))
            )
            .andExpect(status().isOk());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
        Koerperfett testKoerperfett = koerperfettList.get(koerperfettList.size() - 1);
        assertThat(testKoerperfett.getPrivatoderfirma()).isEqualTo(UPDATED_PRIVATODERFIRMA);
        assertThat(testKoerperfett.getKoerpergroesse()).isEqualTo(UPDATED_KOERPERGROESSE);
        assertThat(testKoerperfett.getNackenumfang()).isEqualTo(UPDATED_NACKENUMFANG);
        assertThat(testKoerperfett.getBauchumfang()).isEqualTo(UPDATED_BAUCHUMFANG);
        assertThat(testKoerperfett.getHueftumfang()).isEqualTo(UPDATED_HUEFTUMFANG);
        assertThat(testKoerperfett.getGeschlecht()).isEqualTo(UPDATED_GESCHLECHT);
        assertThat(testKoerperfett.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testKoerperfett.getKoerperfettanteil()).isEqualTo(UPDATED_KOERPERFETTANTEIL);
        assertThat(testKoerperfett.getDatumundZeit()).isEqualTo(UPDATED_DATUMUND_ZEIT);
        assertThat(testKoerperfett.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testKoerperfett.getSuccess()).isEqualTo(UPDATED_SUCCESS);
        assertThat(testKoerperfett.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingKoerperfett() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();
        koerperfett.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoerperfettMockMvc
            .perform(
                put(ENTITY_API_URL_ID, koerperfett.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(koerperfett))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKoerperfett() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();
        koerperfett.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoerperfettMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(koerperfett))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKoerperfett() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();
        koerperfett.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoerperfettMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKoerperfettWithPatch() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();

        // Update the koerperfett using partial update
        Koerperfett partialUpdatedKoerperfett = new Koerperfett();
        partialUpdatedKoerperfett.setId(koerperfett.getId());

        partialUpdatedKoerperfett
            .koerpergroesse(UPDATED_KOERPERGROESSE)
            .nackenumfang(UPDATED_NACKENUMFANG)
            .bauchumfang(UPDATED_BAUCHUMFANG)
            .hueftumfang(UPDATED_HUEFTUMFANG)
            .koerperfettanteil(UPDATED_KOERPERFETTANTEIL)
            .datumundZeit(UPDATED_DATUMUND_ZEIT)
            .url(UPDATED_URL)
            .success(UPDATED_SUCCESS)
            .errorMessage(UPDATED_ERROR_MESSAGE);

        restKoerperfettMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoerperfett.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKoerperfett))
            )
            .andExpect(status().isOk());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
        Koerperfett testKoerperfett = koerperfettList.get(koerperfettList.size() - 1);
        assertThat(testKoerperfett.getPrivatoderfirma()).isEqualTo(DEFAULT_PRIVATODERFIRMA);
        assertThat(testKoerperfett.getKoerpergroesse()).isEqualTo(UPDATED_KOERPERGROESSE);
        assertThat(testKoerperfett.getNackenumfang()).isEqualTo(UPDATED_NACKENUMFANG);
        assertThat(testKoerperfett.getBauchumfang()).isEqualTo(UPDATED_BAUCHUMFANG);
        assertThat(testKoerperfett.getHueftumfang()).isEqualTo(UPDATED_HUEFTUMFANG);
        assertThat(testKoerperfett.getGeschlecht()).isEqualTo(DEFAULT_GESCHLECHT);
        assertThat(testKoerperfett.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testKoerperfett.getKoerperfettanteil()).isEqualTo(UPDATED_KOERPERFETTANTEIL);
        assertThat(testKoerperfett.getDatumundZeit()).isEqualTo(UPDATED_DATUMUND_ZEIT);
        assertThat(testKoerperfett.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testKoerperfett.getSuccess()).isEqualTo(UPDATED_SUCCESS);
        assertThat(testKoerperfett.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateKoerperfettWithPatch() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();

        // Update the koerperfett using partial update
        Koerperfett partialUpdatedKoerperfett = new Koerperfett();
        partialUpdatedKoerperfett.setId(koerperfett.getId());

        partialUpdatedKoerperfett
            .privatoderfirma(UPDATED_PRIVATODERFIRMA)
            .koerpergroesse(UPDATED_KOERPERGROESSE)
            .nackenumfang(UPDATED_NACKENUMFANG)
            .bauchumfang(UPDATED_BAUCHUMFANG)
            .hueftumfang(UPDATED_HUEFTUMFANG)
            .geschlecht(UPDATED_GESCHLECHT)
            .age(UPDATED_AGE)
            .koerperfettanteil(UPDATED_KOERPERFETTANTEIL)
            .datumundZeit(UPDATED_DATUMUND_ZEIT)
            .url(UPDATED_URL)
            .success(UPDATED_SUCCESS)
            .errorMessage(UPDATED_ERROR_MESSAGE);

        restKoerperfettMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoerperfett.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKoerperfett))
            )
            .andExpect(status().isOk());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
        Koerperfett testKoerperfett = koerperfettList.get(koerperfettList.size() - 1);
        assertThat(testKoerperfett.getPrivatoderfirma()).isEqualTo(UPDATED_PRIVATODERFIRMA);
        assertThat(testKoerperfett.getKoerpergroesse()).isEqualTo(UPDATED_KOERPERGROESSE);
        assertThat(testKoerperfett.getNackenumfang()).isEqualTo(UPDATED_NACKENUMFANG);
        assertThat(testKoerperfett.getBauchumfang()).isEqualTo(UPDATED_BAUCHUMFANG);
        assertThat(testKoerperfett.getHueftumfang()).isEqualTo(UPDATED_HUEFTUMFANG);
        assertThat(testKoerperfett.getGeschlecht()).isEqualTo(UPDATED_GESCHLECHT);
        assertThat(testKoerperfett.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testKoerperfett.getKoerperfettanteil()).isEqualTo(UPDATED_KOERPERFETTANTEIL);
        assertThat(testKoerperfett.getDatumundZeit()).isEqualTo(UPDATED_DATUMUND_ZEIT);
        assertThat(testKoerperfett.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testKoerperfett.getSuccess()).isEqualTo(UPDATED_SUCCESS);
        assertThat(testKoerperfett.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingKoerperfett() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();
        koerperfett.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoerperfettMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, koerperfett.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(koerperfett))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKoerperfett() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();
        koerperfett.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoerperfettMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(koerperfett))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKoerperfett() throws Exception {
        int databaseSizeBeforeUpdate = koerperfettRepository.findAll().size();
        koerperfett.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoerperfettMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(koerperfett))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKoerperfett() throws Exception {
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        int databaseSizeBeforeDelete = koerperfettRepository.findAll().size();

        // Delete the koerperfett
        restKoerperfettMockMvc
            .perform(delete(ENTITY_API_URL_ID, koerperfett.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
