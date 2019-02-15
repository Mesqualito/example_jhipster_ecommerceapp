package net.generica.store.web.rest;

import net.generica.store.StoreApp;

import net.generica.store.domain.ProductCategory;
import net.generica.store.domain.Product;
import net.generica.store.repository.ProductCategoryRepository;
import net.generica.store.service.ProductCategoryService;
import net.generica.store.service.dto.ProductCategoryDTO;
import net.generica.store.service.mapper.ProductCategoryMapper;
import net.generica.store.web.rest.errors.ExceptionTranslator;
import net.generica.store.service.dto.ProductCategoryCriteria;
import net.generica.store.service.ProductCategoryQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
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
 * Test class for the ProductCategoryResource REST controller.
 *
 * @see ProductCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreApp.class)
public class ProductCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryQueryService productCategoryQueryService;

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

    private MockMvc restProductCategoryMockMvc;

    private ProductCategory productCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductCategoryResource productCategoryResource = new ProductCategoryResource(productCategoryService, productCategoryQueryService);
        this.restProductCategoryMockMvc = MockMvcBuilders.standaloneSetup(productCategoryResource)
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
    public static ProductCategory createEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productCategory;
    }

    @Before
    public void initTest() {
        productCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCategory() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);
        restProductCategoryMockMvc.perform(post("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // Create the ProductCategory with an existing ID
        productCategory.setId(1L);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryMockMvc.perform(post("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCategoryRepository.findAll().size();
        // set the field null
        productCategory.setName(null);

        // Create the ProductCategory, which fails.
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        restProductCategoryMockMvc.perform(post("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductCategories() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/product-categories/{id}", productCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name equals to DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name is not null
        defaultProductCategoryShouldBeFound("name.specified=true");

        // Get all the productCategoryList where name is null
        defaultProductCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultProductCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the productCategoryList where description equals to UPDATED_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProductCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the productCategoryList where description equals to UPDATED_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description is not null
        defaultProductCategoryShouldBeFound("description.specified=true");

        // Get all the productCategoryList where description is null
        defaultProductCategoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        productCategory.addProduct(product);
        productCategoryRepository.saveAndFlush(productCategory);
        Long productId = product.getId();

        // Get all the productCategoryList where product equals to productId
        defaultProductCategoryShouldBeFound("productId.equals=" + productId);

        // Get all the productCategoryList where product equals to productId + 1
        defaultProductCategoryShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProductCategoryShouldBeFound(String filter) throws Exception {
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProductCategoryMockMvc.perform(get("/api/product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProductCategoryShouldNotBeFound(String filter) throws Exception {
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryMockMvc.perform(get("/api/product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductCategory() throws Exception {
        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/product-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory
        ProductCategory updatedProductCategory = productCategoryRepository.findById(productCategory.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategory are not directly saved in db
        em.detach(updatedProductCategory);
        updatedProductCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(updatedProductCategory);

        restProductCategoryMockMvc.perform(put("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc.perform(put("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        // Delete the productCategory
        restProductCategoryMockMvc.perform(delete("/api/product-categories/{id}", productCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategory.class);
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setId(productCategory1.getId());
        assertThat(productCategory1).isEqualTo(productCategory2);
        productCategory2.setId(2L);
        assertThat(productCategory1).isNotEqualTo(productCategory2);
        productCategory1.setId(null);
        assertThat(productCategory1).isNotEqualTo(productCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryDTO.class);
        ProductCategoryDTO productCategoryDTO1 = new ProductCategoryDTO();
        productCategoryDTO1.setId(1L);
        ProductCategoryDTO productCategoryDTO2 = new ProductCategoryDTO();
        assertThat(productCategoryDTO1).isNotEqualTo(productCategoryDTO2);
        productCategoryDTO2.setId(productCategoryDTO1.getId());
        assertThat(productCategoryDTO1).isEqualTo(productCategoryDTO2);
        productCategoryDTO2.setId(2L);
        assertThat(productCategoryDTO1).isNotEqualTo(productCategoryDTO2);
        productCategoryDTO1.setId(null);
        assertThat(productCategoryDTO1).isNotEqualTo(productCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productCategoryMapper.fromId(null)).isNull();
    }
}
