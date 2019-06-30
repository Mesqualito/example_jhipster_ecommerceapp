package net.generica.store.web.rest;

import net.generica.store.StoreApp;
import net.generica.store.domain.ProductSubstitution;
import net.generica.store.repository.ProductSubstitutionRepository;
import net.generica.store.service.ProductSubstitutionService;
import net.generica.store.web.rest.errors.ExceptionTranslator;
import net.generica.store.service.dto.ProductSubstitutionCriteria;
import net.generica.store.service.ProductSubstitutionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static net.generica.store.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ProductSubstitutionResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
public class ProductSubstitutionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXCHANGEABLE = false;
    private static final Boolean UPDATED_EXCHANGEABLE = true;

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    @Autowired
    private ProductSubstitutionRepository productSubstitutionRepository;

    @Autowired
    private ProductSubstitutionService productSubstitutionService;

    @Autowired
    private ProductSubstitutionQueryService productSubstitutionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductSubstitutionMockMvc;

    private ProductSubstitution productSubstitution;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductSubstitutionResource productSubstitutionResource = new ProductSubstitutionResource(productSubstitutionService, productSubstitutionQueryService);
        this.restProductSubstitutionMockMvc = MockMvcBuilders.standaloneSetup(productSubstitutionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSubstitution createEntity(EntityManager em) {
        ProductSubstitution productSubstitution = new ProductSubstitution()
            .name(DEFAULT_NAME)
            .exchangeable(DEFAULT_EXCHANGEABLE)
            .checked(DEFAULT_CHECKED);
        return productSubstitution;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSubstitution createUpdatedEntity(EntityManager em) {
        ProductSubstitution productSubstitution = new ProductSubstitution()
            .name(UPDATED_NAME)
            .exchangeable(UPDATED_EXCHANGEABLE)
            .checked(UPDATED_CHECKED);
        return productSubstitution;
    }

    @BeforeEach
    public void initTest() {
        productSubstitution = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSubstitution() throws Exception {
        int databaseSizeBeforeCreate = productSubstitutionRepository.findAll().size();

        // Create the ProductSubstitution
        restProductSubstitutionMockMvc.perform(post("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitution)))
            .andExpect(status().isCreated());

        // Validate the ProductSubstitution in the database
        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSubstitution testProductSubstitution = productSubstitutionList.get(productSubstitutionList.size() - 1);
        assertThat(testProductSubstitution.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSubstitution.isExchangeable()).isEqualTo(DEFAULT_EXCHANGEABLE);
        assertThat(testProductSubstitution.isChecked()).isEqualTo(DEFAULT_CHECKED);
    }

    @Test
    @Transactional
    public void createProductSubstitutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSubstitutionRepository.findAll().size();

        // Create the ProductSubstitution with an existing ID
        productSubstitution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSubstitutionMockMvc.perform(post("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitution)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSubstitution in the database
        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkExchangeableIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSubstitutionRepository.findAll().size();
        // set the field null
        productSubstitution.setExchangeable(null);

        // Create the ProductSubstitution, which fails.

        restProductSubstitutionMockMvc.perform(post("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitution)))
            .andExpect(status().isBadRequest());

        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutions() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList
        restProductSubstitutionMockMvc.perform(get("/api/product-substitutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSubstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].exchangeable").value(hasItem(DEFAULT_EXCHANGEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProductSubstitution() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get the productSubstitution
        restProductSubstitutionMockMvc.perform(get("/api/product-substitutions/{id}", productSubstitution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productSubstitution.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.exchangeable").value(DEFAULT_EXCHANGEABLE.booleanValue()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where name equals to DEFAULT_NAME
        defaultProductSubstitutionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productSubstitutionList where name equals to UPDATED_NAME
        defaultProductSubstitutionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductSubstitutionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productSubstitutionList where name equals to UPDATED_NAME
        defaultProductSubstitutionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where name is not null
        defaultProductSubstitutionShouldBeFound("name.specified=true");

        // Get all the productSubstitutionList where name is null
        defaultProductSubstitutionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByExchangeableIsEqualToSomething() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where exchangeable equals to DEFAULT_EXCHANGEABLE
        defaultProductSubstitutionShouldBeFound("exchangeable.equals=" + DEFAULT_EXCHANGEABLE);

        // Get all the productSubstitutionList where exchangeable equals to UPDATED_EXCHANGEABLE
        defaultProductSubstitutionShouldNotBeFound("exchangeable.equals=" + UPDATED_EXCHANGEABLE);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByExchangeableIsInShouldWork() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where exchangeable in DEFAULT_EXCHANGEABLE or UPDATED_EXCHANGEABLE
        defaultProductSubstitutionShouldBeFound("exchangeable.in=" + DEFAULT_EXCHANGEABLE + "," + UPDATED_EXCHANGEABLE);

        // Get all the productSubstitutionList where exchangeable equals to UPDATED_EXCHANGEABLE
        defaultProductSubstitutionShouldNotBeFound("exchangeable.in=" + UPDATED_EXCHANGEABLE);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByExchangeableIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where exchangeable is not null
        defaultProductSubstitutionShouldBeFound("exchangeable.specified=true");

        // Get all the productSubstitutionList where exchangeable is null
        defaultProductSubstitutionShouldNotBeFound("exchangeable.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByCheckedIsEqualToSomething() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where checked equals to DEFAULT_CHECKED
        defaultProductSubstitutionShouldBeFound("checked.equals=" + DEFAULT_CHECKED);

        // Get all the productSubstitutionList where checked equals to UPDATED_CHECKED
        defaultProductSubstitutionShouldNotBeFound("checked.equals=" + UPDATED_CHECKED);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByCheckedIsInShouldWork() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where checked in DEFAULT_CHECKED or UPDATED_CHECKED
        defaultProductSubstitutionShouldBeFound("checked.in=" + DEFAULT_CHECKED + "," + UPDATED_CHECKED);

        // Get all the productSubstitutionList where checked equals to UPDATED_CHECKED
        defaultProductSubstitutionShouldNotBeFound("checked.in=" + UPDATED_CHECKED);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByCheckedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where checked is not null
        defaultProductSubstitutionShouldBeFound("checked.specified=true");

        // Get all the productSubstitutionList where checked is null
        defaultProductSubstitutionShouldNotBeFound("checked.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductSubstitutionShouldBeFound(String filter) throws Exception {
        restProductSubstitutionMockMvc.perform(get("/api/product-substitutions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSubstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].exchangeable").value(hasItem(DEFAULT_EXCHANGEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())));

        // Check, that the count call also returns 1
        restProductSubstitutionMockMvc.perform(get("/api/product-substitutions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductSubstitutionShouldNotBeFound(String filter) throws Exception {
        restProductSubstitutionMockMvc.perform(get("/api/product-substitutions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductSubstitutionMockMvc.perform(get("/api/product-substitutions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductSubstitution() throws Exception {
        // Get the productSubstitution
        restProductSubstitutionMockMvc.perform(get("/api/product-substitutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSubstitution() throws Exception {
        // Initialize the database
        productSubstitutionService.save(productSubstitution);

        int databaseSizeBeforeUpdate = productSubstitutionRepository.findAll().size();

        // Update the productSubstitution
        ProductSubstitution updatedProductSubstitution = productSubstitutionRepository.findById(productSubstitution.getId()).get();
        // Disconnect from session so that the updates on updatedProductSubstitution are not directly saved in db
        em.detach(updatedProductSubstitution);
        updatedProductSubstitution
            .name(UPDATED_NAME)
            .exchangeable(UPDATED_EXCHANGEABLE)
            .checked(UPDATED_CHECKED);

        restProductSubstitutionMockMvc.perform(put("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductSubstitution)))
            .andExpect(status().isOk());

        // Validate the ProductSubstitution in the database
        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeUpdate);
        ProductSubstitution testProductSubstitution = productSubstitutionList.get(productSubstitutionList.size() - 1);
        assertThat(testProductSubstitution.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSubstitution.isExchangeable()).isEqualTo(UPDATED_EXCHANGEABLE);
        assertThat(testProductSubstitution.isChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSubstitution() throws Exception {
        int databaseSizeBeforeUpdate = productSubstitutionRepository.findAll().size();

        // Create the ProductSubstitution

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSubstitutionMockMvc.perform(put("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitution)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSubstitution in the database
        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSubstitution() throws Exception {
        // Initialize the database
        productSubstitutionService.save(productSubstitution);

        int databaseSizeBeforeDelete = productSubstitutionRepository.findAll().size();

        // Delete the productSubstitution
        restProductSubstitutionMockMvc.perform(delete("/api/product-substitutions/{id}", productSubstitution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSubstitution.class);
        ProductSubstitution productSubstitution1 = new ProductSubstitution();
        productSubstitution1.setId(1L);
        ProductSubstitution productSubstitution2 = new ProductSubstitution();
        productSubstitution2.setId(productSubstitution1.getId());
        assertThat(productSubstitution1).isEqualTo(productSubstitution2);
        productSubstitution2.setId(2L);
        assertThat(productSubstitution1).isNotEqualTo(productSubstitution2);
        productSubstitution1.setId(null);
        assertThat(productSubstitution1).isNotEqualTo(productSubstitution2);
    }
}
