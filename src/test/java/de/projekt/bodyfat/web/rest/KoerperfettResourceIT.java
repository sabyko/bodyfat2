package de.projekt.bodyfat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.projekt.bodyfat.IntegrationTest;
import de.projekt.bodyfat.domain.Koerperfett;
import de.projekt.bodyfat.repository.KoerperfettRepository;
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

    private static final Boolean MAENNLICH_PRIVATODERFIRMA = true;
    private static final Boolean WEIBLICH_PRIVATODERFIRMA = false;

    private static final Integer MAENNLICH_KOERPERGROESSE = 180;
    private static final Integer WEIBLICH_KOERPERGROESSE = 160;

    private static final Integer MAENNLICH_NACKENUMFANG = 40;
    private static final Integer WEIBLICH_NACKENUMFANG = 30;

    private static final Integer MAENNLICH_BAUCHUMFANG = 100;
    private static final Integer WEIBLICH_BAUCHUMFANG = 80;

    private static final Integer MAENNLICH_HUEFTUMFANG = 0;
    private static final Integer WEIBLICH_HUEFTUMFANG = 80;

    private static final String MAENNLICH_GESCHLECHT = "männlich";
    private static final String WEIBLICH_GESCHLECHT = "weiblich";

    private static final Integer MAENNLICH_AGE = 42;
    private static final Integer WEIBLICH_AGE = 25;

    private static final Double MAENNLICH_KOERPERFETTANTEIL =
        (86.01 * Math.log10(MAENNLICH_BAUCHUMFANG - MAENNLICH_NACKENUMFANG) - 70.041 * Math.log10(MAENNLICH_KOERPERGROESSE) + 30.3);
    private static final Double WEIBLICH_KOERPERFETTANTEIL =
        (
            163.205 *
            Math.log10(WEIBLICH_BAUCHUMFANG + WEIBLICH_HUEFTUMFANG - WEIBLICH_NACKENUMFANG) -
            97.684 *
            Math.log10(WEIBLICH_KOERPERGROESSE) -
            104.912
        );

    private static final Instant MAENNLICH_DATUMUND_ZEIT = Instant.ofEpochMilli(0L);
    private static final Instant WEIBLICH_DATUMUND_ZEIT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String MAENNLICH_URL = "TestM.de";
    private static final String WEIBLICH_URL = "TestW.de";

    private static final Boolean MAENNLICH_SUCCESS = true;
    private static final Boolean WEIBLICH_SUCCESS = true;

    private static final String MAENNLICH_ERROR_MESSAGE = null;
    private static final String WEIBLICH_ERROR_MESSAGE = null;

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
            .privatoderfirma(MAENNLICH_PRIVATODERFIRMA)
            .koerpergroesse(MAENNLICH_KOERPERGROESSE)
            .nackenumfang(MAENNLICH_NACKENUMFANG)
            .bauchumfang(MAENNLICH_BAUCHUMFANG)
            .hueftumfang(MAENNLICH_HUEFTUMFANG)
            .geschlecht(MAENNLICH_GESCHLECHT)
            .age(MAENNLICH_AGE)
            .koerperfettanteil(MAENNLICH_KOERPERFETTANTEIL)
            .datumundZeit(MAENNLICH_DATUMUND_ZEIT)
            .url(MAENNLICH_URL)
            .success(MAENNLICH_SUCCESS)
            .errorMessage(MAENNLICH_ERROR_MESSAGE);
        return koerperfett;
    }

    /**
     * Create an WEIBLICH entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koerperfett createWEIBLICHEntity(EntityManager em) {
        Koerperfett koerperfett = new Koerperfett()
            .privatoderfirma(WEIBLICH_PRIVATODERFIRMA)
            .koerpergroesse(WEIBLICH_KOERPERGROESSE)
            .nackenumfang(WEIBLICH_NACKENUMFANG)
            .bauchumfang(WEIBLICH_BAUCHUMFANG)
            .hueftumfang(WEIBLICH_HUEFTUMFANG)
            .geschlecht(WEIBLICH_GESCHLECHT)
            .age(WEIBLICH_AGE)
            .koerperfettanteil(WEIBLICH_KOERPERFETTANTEIL)
            .datumundZeit(WEIBLICH_DATUMUND_ZEIT)
            .url(WEIBLICH_URL)
            .success(WEIBLICH_SUCCESS)
            .errorMessage(WEIBLICH_ERROR_MESSAGE);
        return koerperfett;
    }

    @BeforeEach
    public void initTest() {
        koerperfett = createEntity(em);
    }

    @BeforeEach
    public void initwTest() {
        koerperfett = createWEIBLICHEntity(em);
    }

    @Test
    @Transactional
    void createKoerperfettMaenlich() throws Exception {
        initTest();
        int databaseSizeBeforeCreate = koerperfettRepository.findAll().size();
        // Create the Koerperfett
        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isCreated());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeCreate + 1);
        Koerperfett testKoerperfett = koerperfettList.get(koerperfettList.size() - 1);
        assertThat(testKoerperfett.getPrivatoderfirma()).isEqualTo(MAENNLICH_PRIVATODERFIRMA);
        assertThat(testKoerperfett.getKoerpergroesse()).isEqualTo(MAENNLICH_KOERPERGROESSE);
        assertThat(testKoerperfett.getNackenumfang()).isEqualTo(MAENNLICH_NACKENUMFANG);
        assertThat(testKoerperfett.getBauchumfang()).isEqualTo(MAENNLICH_BAUCHUMFANG);
        assertThat(testKoerperfett.getHueftumfang()).isEqualTo(MAENNLICH_HUEFTUMFANG);
        assertThat(testKoerperfett.getGeschlecht()).isEqualTo(MAENNLICH_GESCHLECHT);
        assertThat(testKoerperfett.getAge()).isEqualTo(MAENNLICH_AGE);
        assertThat(testKoerperfett.getKoerperfettanteil()).isEqualTo(MAENNLICH_KOERPERFETTANTEIL);
        assertThat(testKoerperfett.getDatumundZeit()).isEqualTo(MAENNLICH_DATUMUND_ZEIT);
        assertThat(testKoerperfett.getUrl()).isEqualTo(MAENNLICH_URL);
        assertThat(testKoerperfett.getSuccess()).isEqualTo(MAENNLICH_SUCCESS);
        assertThat(testKoerperfett.getErrorMessage()).isEqualTo(MAENNLICH_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void createKoerperfettWeiblich() throws Exception {
        initwTest();
        int databaseSizeBeforeCreate = koerperfettRepository.findAll().size();

        restKoerperfettMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(koerperfett)))
            .andExpect(status().isCreated());

        // Validate the Koerperfett in the database
        List<Koerperfett> koerperfettList = koerperfettRepository.findAll();
        assertThat(koerperfettList).hasSize(databaseSizeBeforeCreate + 1);
        Koerperfett testKoerperfett = koerperfettList.get(koerperfettList.size() - 1);
        assertThat(testKoerperfett.getPrivatoderfirma()).isEqualTo(WEIBLICH_PRIVATODERFIRMA);
        assertThat(testKoerperfett.getKoerpergroesse()).isEqualTo(WEIBLICH_KOERPERGROESSE);
        assertThat(testKoerperfett.getNackenumfang()).isEqualTo(WEIBLICH_NACKENUMFANG);
        assertThat(testKoerperfett.getBauchumfang()).isEqualTo(WEIBLICH_BAUCHUMFANG);
        assertThat(testKoerperfett.getHueftumfang()).isEqualTo(WEIBLICH_HUEFTUMFANG);
        assertThat(testKoerperfett.getGeschlecht()).isEqualTo(WEIBLICH_GESCHLECHT);
        assertThat(testKoerperfett.getAge()).isEqualTo(WEIBLICH_AGE);
        assertThat(testKoerperfett.getKoerperfettanteil()).isEqualTo(WEIBLICH_KOERPERFETTANTEIL);
        assertThat(testKoerperfett.getDatumundZeit()).isEqualTo(WEIBLICH_DATUMUND_ZEIT);
        assertThat(testKoerperfett.getUrl()).isEqualTo(WEIBLICH_URL);
        assertThat(testKoerperfett.getSuccess()).isEqualTo(WEIBLICH_SUCCESS);
        assertThat(testKoerperfett.getErrorMessage()).isEqualTo(WEIBLICH_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void checkPrivatoderfirmaIsRequired() throws Exception {
        initTest();
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
        initTest();
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
        initTest();
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
        initTest();
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
        initTest();
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
        initTest();
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
        initTest();
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get all the koerperfettList
        restKoerperfettMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koerperfett.getId().intValue())))
            .andExpect(jsonPath("$.[*].privatoderfirma").value(hasItem(MAENNLICH_PRIVATODERFIRMA.booleanValue())))
            .andExpect(jsonPath("$.[*].koerpergroesse").value(hasItem(MAENNLICH_KOERPERGROESSE)))
            .andExpect(jsonPath("$.[*].nackenumfang").value(hasItem(MAENNLICH_NACKENUMFANG)))
            .andExpect(jsonPath("$.[*].bauchumfang").value(hasItem(MAENNLICH_BAUCHUMFANG)))
            .andExpect(jsonPath("$.[*].hueftumfang").value(hasItem(MAENNLICH_HUEFTUMFANG)))
            .andExpect(jsonPath("$.[*].geschlecht").value(hasItem(MAENNLICH_GESCHLECHT)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(MAENNLICH_AGE)))
            .andExpect(jsonPath("$.[*].koerperfettanteil").value(hasItem(MAENNLICH_KOERPERFETTANTEIL.doubleValue())))
            .andExpect(jsonPath("$.[*].datumundZeit").value(hasItem(MAENNLICH_DATUMUND_ZEIT.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(MAENNLICH_URL)))
            .andExpect(jsonPath("$.[*].success").value(hasItem(MAENNLICH_SUCCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(MAENNLICH_ERROR_MESSAGE)));
    }

    @Test
    @Transactional
    void getKoerperfett() throws Exception {
        initTest();
        // Initialize the database
        koerperfettRepository.saveAndFlush(koerperfett);

        // Get the koerperfett
        restKoerperfettMockMvc
            .perform(get(ENTITY_API_URL_ID, koerperfett.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(koerperfett.getId().intValue()))
            .andExpect(jsonPath("$.privatoderfirma").value(MAENNLICH_PRIVATODERFIRMA.booleanValue()))
            .andExpect(jsonPath("$.koerpergroesse").value(MAENNLICH_KOERPERGROESSE))
            .andExpect(jsonPath("$.nackenumfang").value(MAENNLICH_NACKENUMFANG))
            .andExpect(jsonPath("$.bauchumfang").value(MAENNLICH_BAUCHUMFANG))
            .andExpect(jsonPath("$.hueftumfang").value(MAENNLICH_HUEFTUMFANG))
            .andExpect(jsonPath("$.geschlecht").value(MAENNLICH_GESCHLECHT))
            .andExpect(jsonPath("$.age").value(MAENNLICH_AGE))
            .andExpect(jsonPath("$.koerperfettanteil").value(MAENNLICH_KOERPERFETTANTEIL.doubleValue()))
            .andExpect(jsonPath("$.datumundZeit").value(MAENNLICH_DATUMUND_ZEIT.toString()))
            .andExpect(jsonPath("$.url").value(MAENNLICH_URL))
            .andExpect(jsonPath("$.success").value(MAENNLICH_SUCCESS.booleanValue()))
            .andExpect(jsonPath("$.errorMessage").value(MAENNLICH_ERROR_MESSAGE));
    }

    @Test
    @Transactional
    void deleteKoerperfett() throws Exception {
        initTest();
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
