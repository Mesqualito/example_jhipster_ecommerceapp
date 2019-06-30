package net.generica.store.web.rest;

import net.generica.store.StoreApp;
import net.generica.store.domain.ProductReference;
import net.generica.store.domain.Product;
import net.generica.store.repository.ProductReferenceRepository;
import net.generica.store.service.ProductReferenceService;
import net.generica.store.service.dto.ProductReferenceDTO;
import net.generica.store.service.mapper.ProductReferenceMapper;
import net.generica.store.web.rest.errors.ExceptionTranslator;
import net.generica.store.service.dto.ProductReferenceCriteria;
import net.generica.store.service.ProductReferenceQueryService;

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
 * Integration tests for the {@Link ProductReferenceResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
public class ProductReferenceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REF_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REF_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ProductReferenceRepository productReferenceRepository;

    @Autowired
    private ProductReferenceMapper productReferenceMapper;

    @Autowired
    private ProductReferenceService productReferenceService;

    @Autowired
    private ProductReferenceQueryService productReferenceQueryService;

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

    private MockMvc restProductReferenceMockMvc;

    private ProductReference productReference;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductReferenceResource productReferenceResource = new ProductReferenceResource(productReferenceService, productReferenceQueryService);
        this.restProductReferenceMockMvc = MockMvcBuilders.standaloneSetup(productReferenceResource)
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
    public static ProductReference createEntity(EntityManager em) {
        ProductReference productReference = new ProductReference()
            .name(DEFAULT_NAME)
            .refName(DEFAULT_REF_NAME)
            .reference(DEFAULT_REFERENCE)
            .type(DEFAULT_TYPE);
        return productReference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductReference createUpdatedEntity(EntityManager em) {
        ProductReference productReference = new ProductReference()
            .name(UPDATED_NAME)
            .refName(UPDATED_REF_NAME)
            .reference(UPDATED_REFERENCE)
            .type(UPDATED_TYPE);
        return productReference;
    }

    @BeforeEach
    public void initTest() {
        productReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductReference() throws Exception {
        int databaseSizeBeforeCreate = productReferenceRepository.findAll().size();

        // Create the ProductReference
        ProductReferenceDTO productReferenceDTO = productReferenceMapper.toDto(productReference);
        restProductReferenceMockMvc.perform(post("/api/product-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductReference in the database
        List<ProductReference> productReferenceList = productReferenceRepository.findAll();
        assertThat(productReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductReference testProductReference = productReferenceList.get(productReferenceList.size() - 1);
        assertThat(testProductReference.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductReference.getRefName()).isEqualTo(DEFAULT_REF_NAME);
        assertThat(testProductReference.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testProductReference.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createProductReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productReferenceRepository.findAll().size();

        // Create the ProductReference with an existing ID
        productReference.setId(1L);
        ProductReferenceDTO productReferenceDTO = productReferenceMapper.toDto(productReference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductReferenceMockMvc.perform(post("/api/product-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductReference in the database
        List<ProductReference> productReferenceList = productReferenceRepository.findAll();
        assertThat(productReferenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRefNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productReferenceRepository.findAll().size();
        // set the field null
        productReference.setRefName(null);

        // Create the ProductReference, which fails.
        ProductReferenceDTO productReferenceDTO = productReferenceMapper.toDto(productReference);

        restProductReferenceMockMvc.perform(post("/api/product-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<ProductReference> productReferenceList = productReferenceRepository.findAll();
        assertThat(productReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductReferences() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList
        restProductReferenceMockMvc.perform(get("/api/product-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].refName").value(hasItem(DEFAULT_REF_NAME.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductReference() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get the productReference
        restProductReferenceMockMvc.perform(get("/api/product-references/{id}", productReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productReference.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.refName").value(DEFAULT_REF_NAME.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllProductReferencesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where name equals to DEFAULT_NAME
        defaultProductReferenceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productReferenceList where name equals to UPDATED_NAME
        defaultProductReferenceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductReferencesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductReferenceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productReferenceList where name equals to UPDATED_NAME
        defaultProductReferenceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductReferencesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where name is not null
        defaultProductReferenceShouldBeFound("name.specified=true");

        // Get all the productReferenceList where name is null
        defaultProductReferenceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductReferencesByRefNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where refName equals to DEFAULT_REF_NAME
        defaultProductReferenceShouldBeFound("refName.equals=" + DEFAULT_REF_NAME);

        // Get all the productReferenceList where refName equals to UPDATED_REF_NAME
        defaultProductReferenceShouldNotBeFound("refName.equals=" + UPDATED_REF_NAME);
    }

    @Test
    @Transactional
    public void getAllProductReferencesByRefNameIsInShouldWork() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where refName in DEFAULT_REF_NAME or UPDATED_REF_NAME
        defaultProductReferenceShouldBeFound("refName.in=" + DEFAULT_REF_NAME + "," + UPDATED_REF_NAME);

        // Get all the productReferenceList where refName equals to UPDATED_REF_NAME
        defaultProductReferenceShouldNotBeFound("refName.in=" + UPDATED_REF_NAME);
    }

    @Test
    @Transactional
    public void getAllProductReferencesByRefNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where refName is not null
        defaultProductReferenceShouldBeFound("refName.specified=true");

        // Get all the productReferenceList where refName is null
        defaultProductReferenceShouldNotBeFound("refName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductReferencesByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where reference equals to DEFAULT_REFERENCE
        defaultProductReferenceShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the productReferenceList where reference equals to UPDATED_REFERENCE
        defaultProductReferenceShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllProductReferencesByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultProductReferenceShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the productReferenceList where reference equals to UPDATED_REFERENCE
        defaultProductReferenceShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllProductReferencesByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where reference is not null
        defaultProductReferenceShouldBeFound("reference.specified=true");

        // Get all the productReferenceList where reference is null
        defaultProductReferenceShouldNotBeFound("reference.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductReferencesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where type equals to DEFAULT_TYPE
        defaultProductReferenceShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productReferenceList where type equals to UPDATED_TYPE
        defaultProductReferenceShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductReferencesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductReferenceShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productReferenceList where type equals to UPDATED_TYPE
        defaultProductReferenceShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductReferencesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        // Get all the productReferenceList where type is not null
        defaultProductReferenceShouldBeFound("type.specified=true");

        // Get all the productReferenceList where type is null
        defaultProductReferenceShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductReferencesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productReference.setProduct(product);
        productReferenceRepository.saveAndFlush(productReference);
        Long productId = product.getId();

        // Get all the productReferenceList where product equals to productId
        defaultProductReferenceShouldBeFound("productId.equals=" + productId);

        // Get all the productReferenceList where product equals to productId + 1
        defaultProductReferenceShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductReferenceShouldBeFound(String filter) throws Exception {
        restProductReferenceMockMvc.perform(get("/api/product-references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].refName").value(hasItem(DEFAULT_REF_NAME)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restProductReferenceMockMvc.perform(get("/api/product-references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductReferenceShouldNotBeFound(String filter) throws Exception {
        restProductReferenceMockMvc.perform(get("/api/product-references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductReferenceMockMvc.perform(get("/api/product-references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductReference() throws Exception {
        // Get the productReference
        restProductReferenceMockMvc.perform(get("/api/product-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductReference() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        int databaseSizeBeforeUpdate = productReferenceRepository.findAll().size();

        // Update the productReference
        ProductReference updatedProductReference = productReferenceRepository.findById(productReference.getId()).get();
        // Disconnect from session so that the updates on updatedProductReference are not directly saved in db
        em.detach(updatedProductReference);
        updatedProductReference
            .name(UPDATED_NAME)
            .refName(UPDATED_REF_NAME)
            .reference(UPDATED_REFERENCE)
            .type(UPDATED_TYPE);
        ProductReferenceDTO productReferenceDTO = productReferenceMapper.toDto(updatedProductReference);

        restProductReferenceMockMvc.perform(put("/api/product-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReferenceDTO)))
            .andExpect(status().isOk());

        // Validate the ProductReference in the database
        List<ProductReference> productReferenceList = productReferenceRepository.findAll();
        assertThat(productReferenceList).hasSize(databaseSizeBeforeUpdate);
        ProductReference testProductReference = productReferenceList.get(productReferenceList.size() - 1);
        assertThat(testProductReference.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductReference.getRefName()).isEqualTo(UPDATED_REF_NAME);
        assertThat(testProductReference.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testProductReference.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductReference() throws Exception {
        int databaseSizeBeforeUpdate = productReferenceRepository.findAll().size();

        // Create the ProductReference
        ProductReferenceDTO productReferenceDTO = productReferenceMapper.toDto(productReference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductReferenceMockMvc.perform(put("/api/product-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductReference in the database
        List<ProductReference> productReferenceList = productReferenceRepository.findAll();
        assertThat(productReferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductReference() throws Exception {
        // Initialize the database
        productReferenceRepository.saveAndFlush(productReference);

        int databaseSizeBeforeDelete = productReferenceRepository.findAll().size();

        // Delete the productReference
        restProductReferenceMockMvc.perform(delete("/api/product-references/{id}", productReference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductReference> productReferenceList = productReferenceRepository.findAll();
        assertThat(productReferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductReference.class);
        ProductReference productReference1 = new ProductReference();
        productReference1.setId(1L);
        ProductReference productReference2 = new ProductReference();
        productReference2.setId(productReference1.getId());
        assertThat(productReference1).isEqualTo(productReference2);
        productReference2.setId(2L);
        assertThat(productReference1).isNotEqualTo(productReference2);
        productReference1.setId(null);
        assertThat(productReference1).isNotEqualTo(productReference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductReferenceDTO.class);
        ProductReferenceDTO productReferenceDTO1 = new ProductReferenceDTO();
        productReferenceDTO1.setId(1L);
        ProductReferenceDTO productReferenceDTO2 = new ProductReferenceDTO();
        assertThat(productReferenceDTO1).isNotEqualTo(productReferenceDTO2);
        productReferenceDTO2.setId(productReferenceDTO1.getId());
        assertThat(productReferenceDTO1).isEqualTo(productReferenceDTO2);
        productReferenceDTO2.setId(2L);
        assertThat(productReferenceDTO1).isNotEqualTo(productReferenceDTO2);
        productReferenceDTO1.setId(null);
        assertThat(productReferenceDTO1).isNotEqualTo(productReferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productReferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productReferenceMapper.fromId(null)).isNull();
    }
}
