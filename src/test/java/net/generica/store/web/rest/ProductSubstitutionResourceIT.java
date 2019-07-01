package net.generica.store.web.rest;

import net.generica.store.StoreApp;
import net.generica.store.domain.ProductSubstitution;
import net.generica.store.domain.Product;
import net.generica.store.repository.ProductSubstitutionRepository;
import net.generica.store.service.ProductSubstitutionService;
import net.generica.store.service.dto.ProductSubstitutionDTO;
import net.generica.store.service.mapper.ProductSubstitutionMapper;
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

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXCHANGEABLE = false;
    private static final Boolean UPDATED_EXCHANGEABLE = true;

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    @Autowired
    private ProductSubstitutionRepository productSubstitutionRepository;

    @Autowired
    private ProductSubstitutionMapper productSubstitutionMapper;

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
            .productName(DEFAULT_PRODUCT_NAME)
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
            .productName(UPDATED_PRODUCT_NAME)
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
        ProductSubstitutionDTO productSubstitutionDTO = productSubstitutionMapper.toDto(productSubstitution);
        restProductSubstitutionMockMvc.perform(post("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitutionDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductSubstitution in the database
        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSubstitution testProductSubstitution = productSubstitutionList.get(productSubstitutionList.size() - 1);
        assertThat(testProductSubstitution.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProductSubstitution.isExchangeable()).isEqualTo(DEFAULT_EXCHANGEABLE);
        assertThat(testProductSubstitution.isChecked()).isEqualTo(DEFAULT_CHECKED);
    }

    @Test
    @Transactional
    public void createProductSubstitutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSubstitutionRepository.findAll().size();

        // Create the ProductSubstitution with an existing ID
        productSubstitution.setId(1L);
        ProductSubstitutionDTO productSubstitutionDTO = productSubstitutionMapper.toDto(productSubstitution);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSubstitutionMockMvc.perform(post("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitutionDTO)))
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
        ProductSubstitutionDTO productSubstitutionDTO = productSubstitutionMapper.toDto(productSubstitution);

        restProductSubstitutionMockMvc.perform(post("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitutionDTO)))
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
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
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
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.exchangeable").value(DEFAULT_EXCHANGEABLE.booleanValue()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where productName equals to DEFAULT_PRODUCT_NAME
        defaultProductSubstitutionShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productSubstitutionList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductSubstitutionShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultProductSubstitutionShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the productSubstitutionList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductSubstitutionShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductSubstitutionsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        // Get all the productSubstitutionList where productName is not null
        defaultProductSubstitutionShouldBeFound("productName.specified=true");

        // Get all the productSubstitutionList where productName is null
        defaultProductSubstitutionShouldNotBeFound("productName.specified=false");
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

    @Test
    @Transactional
    public void getAllProductSubstitutionsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productSubstitution.addProduct(product);
        productSubstitutionRepository.saveAndFlush(productSubstitution);
        Long productId = product.getId();

        // Get all the productSubstitutionList where product equals to productId
        defaultProductSubstitutionShouldBeFound("productId.equals=" + productId);

        // Get all the productSubstitutionList where product equals to productId + 1
        defaultProductSubstitutionShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductSubstitutionShouldBeFound(String filter) throws Exception {
        restProductSubstitutionMockMvc.perform(get("/api/product-substitutions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSubstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
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
        productSubstitutionRepository.saveAndFlush(productSubstitution);

        int databaseSizeBeforeUpdate = productSubstitutionRepository.findAll().size();

        // Update the productSubstitution
        ProductSubstitution updatedProductSubstitution = productSubstitutionRepository.findById(productSubstitution.getId()).get();
        // Disconnect from session so that the updates on updatedProductSubstitution are not directly saved in db
        em.detach(updatedProductSubstitution);
        updatedProductSubstitution
            .productName(UPDATED_PRODUCT_NAME)
            .exchangeable(UPDATED_EXCHANGEABLE)
            .checked(UPDATED_CHECKED);
        ProductSubstitutionDTO productSubstitutionDTO = productSubstitutionMapper.toDto(updatedProductSubstitution);

        restProductSubstitutionMockMvc.perform(put("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitutionDTO)))
            .andExpect(status().isOk());

        // Validate the ProductSubstitution in the database
        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeUpdate);
        ProductSubstitution testProductSubstitution = productSubstitutionList.get(productSubstitutionList.size() - 1);
        assertThat(testProductSubstitution.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductSubstitution.isExchangeable()).isEqualTo(UPDATED_EXCHANGEABLE);
        assertThat(testProductSubstitution.isChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSubstitution() throws Exception {
        int databaseSizeBeforeUpdate = productSubstitutionRepository.findAll().size();

        // Create the ProductSubstitution
        ProductSubstitutionDTO productSubstitutionDTO = productSubstitutionMapper.toDto(productSubstitution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSubstitutionMockMvc.perform(put("/api/product-substitutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSubstitutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSubstitution in the database
        List<ProductSubstitution> productSubstitutionList = productSubstitutionRepository.findAll();
        assertThat(productSubstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSubstitution() throws Exception {
        // Initialize the database
        productSubstitutionRepository.saveAndFlush(productSubstitution);

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

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSubstitutionDTO.class);
        ProductSubstitutionDTO productSubstitutionDTO1 = new ProductSubstitutionDTO();
        productSubstitutionDTO1.setId(1L);
        ProductSubstitutionDTO productSubstitutionDTO2 = new ProductSubstitutionDTO();
        assertThat(productSubstitutionDTO1).isNotEqualTo(productSubstitutionDTO2);
        productSubstitutionDTO2.setId(productSubstitutionDTO1.getId());
        assertThat(productSubstitutionDTO1).isEqualTo(productSubstitutionDTO2);
        productSubstitutionDTO2.setId(2L);
        assertThat(productSubstitutionDTO1).isNotEqualTo(productSubstitutionDTO2);
        productSubstitutionDTO1.setId(null);
        assertThat(productSubstitutionDTO1).isNotEqualTo(productSubstitutionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productSubstitutionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productSubstitutionMapper.fromId(null)).isNull();
    }
}
