package net.generica.store.web.rest;

import net.generica.store.StoreApp;
import net.generica.store.domain.ShopImage;
import net.generica.store.domain.Product;
import net.generica.store.repository.ShopImageRepository;
import net.generica.store.service.ShopImageService;
import net.generica.store.service.dto.ShopImageDTO;
import net.generica.store.service.mapper.ShopImageMapper;
import net.generica.store.web.rest.errors.ExceptionTranslator;
import net.generica.store.service.dto.ShopImageCriteria;
import net.generica.store.service.ShopImageQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static net.generica.store.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import net.generica.store.domain.enumeration.Size;
/**
 * Integration tests for the {@Link ShopImageResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
public class ShopImageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HERST_ART_NR = "AAAAAAAAAA";
    private static final String UPDATED_HERST_ART_NR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Size DEFAULT_SIZE = Size.S;
    private static final Size UPDATED_SIZE = Size.M;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ShopImageRepository shopImageRepository;

    @Autowired
    private ShopImageMapper shopImageMapper;

    @Autowired
    private ShopImageService shopImageService;

    @Autowired
    private ShopImageQueryService shopImageQueryService;

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

    private MockMvc restShopImageMockMvc;

    private ShopImage shopImage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShopImageResource shopImageResource = new ShopImageResource(shopImageService, shopImageQueryService);
        this.restShopImageMockMvc = MockMvcBuilders.standaloneSetup(shopImageResource)
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
    public static ShopImage createEntity(EntityManager em) {
        ShopImage shopImage = new ShopImage()
            .name(DEFAULT_NAME)
            .herstArtNr(DEFAULT_HERST_ART_NR)
            .order(DEFAULT_ORDER)
            .size(DEFAULT_SIZE)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return shopImage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShopImage createUpdatedEntity(EntityManager em) {
        ShopImage shopImage = new ShopImage()
            .name(UPDATED_NAME)
            .herstArtNr(UPDATED_HERST_ART_NR)
            .order(UPDATED_ORDER)
            .size(UPDATED_SIZE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return shopImage;
    }

    @BeforeEach
    public void initTest() {
        shopImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createShopImage() throws Exception {
        int databaseSizeBeforeCreate = shopImageRepository.findAll().size();

        // Create the ShopImage
        ShopImageDTO shopImageDTO = shopImageMapper.toDto(shopImage);
        restShopImageMockMvc.perform(post("/api/shop-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopImageDTO)))
            .andExpect(status().isCreated());

        // Validate the ShopImage in the database
        List<ShopImage> shopImageList = shopImageRepository.findAll();
        assertThat(shopImageList).hasSize(databaseSizeBeforeCreate + 1);
        ShopImage testShopImage = shopImageList.get(shopImageList.size() - 1);
        assertThat(testShopImage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShopImage.getHerstArtNr()).isEqualTo(DEFAULT_HERST_ART_NR);
        assertThat(testShopImage.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testShopImage.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testShopImage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testShopImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testShopImage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createShopImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shopImageRepository.findAll().size();

        // Create the ShopImage with an existing ID
        shopImage.setId(1L);
        ShopImageDTO shopImageDTO = shopImageMapper.toDto(shopImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShopImageMockMvc.perform(post("/api/shop-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShopImage in the database
        List<ShopImage> shopImageList = shopImageRepository.findAll();
        assertThat(shopImageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopImageRepository.findAll().size();
        // set the field null
        shopImage.setName(null);

        // Create the ShopImage, which fails.
        ShopImageDTO shopImageDTO = shopImageMapper.toDto(shopImage);

        restShopImageMockMvc.perform(post("/api/shop-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopImageDTO)))
            .andExpect(status().isBadRequest());

        List<ShopImage> shopImageList = shopImageRepository.findAll();
        assertThat(shopImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHerstArtNrIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopImageRepository.findAll().size();
        // set the field null
        shopImage.setHerstArtNr(null);

        // Create the ShopImage, which fails.
        ShopImageDTO shopImageDTO = shopImageMapper.toDto(shopImage);

        restShopImageMockMvc.perform(post("/api/shop-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopImageDTO)))
            .andExpect(status().isBadRequest());

        List<ShopImage> shopImageList = shopImageRepository.findAll();
        assertThat(shopImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopImageRepository.findAll().size();
        // set the field null
        shopImage.setSize(null);

        // Create the ShopImage, which fails.
        ShopImageDTO shopImageDTO = shopImageMapper.toDto(shopImage);

        restShopImageMockMvc.perform(post("/api/shop-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopImageDTO)))
            .andExpect(status().isBadRequest());

        List<ShopImage> shopImageList = shopImageRepository.findAll();
        assertThat(shopImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShopImages() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList
        restShopImageMockMvc.perform(get("/api/shop-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].herstArtNr").value(hasItem(DEFAULT_HERST_ART_NR.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getShopImage() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get the shopImage
        restShopImageMockMvc.perform(get("/api/shop-images/{id}", shopImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shopImage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.herstArtNr").value(DEFAULT_HERST_ART_NR.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getAllShopImagesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where name equals to DEFAULT_NAME
        defaultShopImageShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the shopImageList where name equals to UPDATED_NAME
        defaultShopImageShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllShopImagesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where name in DEFAULT_NAME or UPDATED_NAME
        defaultShopImageShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the shopImageList where name equals to UPDATED_NAME
        defaultShopImageShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllShopImagesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where name is not null
        defaultShopImageShouldBeFound("name.specified=true");

        // Get all the shopImageList where name is null
        defaultShopImageShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllShopImagesByHerstArtNrIsEqualToSomething() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where herstArtNr equals to DEFAULT_HERST_ART_NR
        defaultShopImageShouldBeFound("herstArtNr.equals=" + DEFAULT_HERST_ART_NR);

        // Get all the shopImageList where herstArtNr equals to UPDATED_HERST_ART_NR
        defaultShopImageShouldNotBeFound("herstArtNr.equals=" + UPDATED_HERST_ART_NR);
    }

    @Test
    @Transactional
    public void getAllShopImagesByHerstArtNrIsInShouldWork() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where herstArtNr in DEFAULT_HERST_ART_NR or UPDATED_HERST_ART_NR
        defaultShopImageShouldBeFound("herstArtNr.in=" + DEFAULT_HERST_ART_NR + "," + UPDATED_HERST_ART_NR);

        // Get all the shopImageList where herstArtNr equals to UPDATED_HERST_ART_NR
        defaultShopImageShouldNotBeFound("herstArtNr.in=" + UPDATED_HERST_ART_NR);
    }

    @Test
    @Transactional
    public void getAllShopImagesByHerstArtNrIsNullOrNotNull() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where herstArtNr is not null
        defaultShopImageShouldBeFound("herstArtNr.specified=true");

        // Get all the shopImageList where herstArtNr is null
        defaultShopImageShouldNotBeFound("herstArtNr.specified=false");
    }

    @Test
    @Transactional
    public void getAllShopImagesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where order equals to DEFAULT_ORDER
        defaultShopImageShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the shopImageList where order equals to UPDATED_ORDER
        defaultShopImageShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllShopImagesByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultShopImageShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the shopImageList where order equals to UPDATED_ORDER
        defaultShopImageShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllShopImagesByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where order is not null
        defaultShopImageShouldBeFound("order.specified=true");

        // Get all the shopImageList where order is null
        defaultShopImageShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllShopImagesByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where order greater than or equals to DEFAULT_ORDER
        defaultShopImageShouldBeFound("order.greaterOrEqualThan=" + DEFAULT_ORDER);

        // Get all the shopImageList where order greater than or equals to UPDATED_ORDER
        defaultShopImageShouldNotBeFound("order.greaterOrEqualThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllShopImagesByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where order less than or equals to DEFAULT_ORDER
        defaultShopImageShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the shopImageList where order less than or equals to UPDATED_ORDER
        defaultShopImageShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }


    @Test
    @Transactional
    public void getAllShopImagesBySizeIsEqualToSomething() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where size equals to DEFAULT_SIZE
        defaultShopImageShouldBeFound("size.equals=" + DEFAULT_SIZE);

        // Get all the shopImageList where size equals to UPDATED_SIZE
        defaultShopImageShouldNotBeFound("size.equals=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllShopImagesBySizeIsInShouldWork() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where size in DEFAULT_SIZE or UPDATED_SIZE
        defaultShopImageShouldBeFound("size.in=" + DEFAULT_SIZE + "," + UPDATED_SIZE);

        // Get all the shopImageList where size equals to UPDATED_SIZE
        defaultShopImageShouldNotBeFound("size.in=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllShopImagesBySizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where size is not null
        defaultShopImageShouldBeFound("size.specified=true");

        // Get all the shopImageList where size is null
        defaultShopImageShouldNotBeFound("size.specified=false");
    }

    @Test
    @Transactional
    public void getAllShopImagesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where description equals to DEFAULT_DESCRIPTION
        defaultShopImageShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the shopImageList where description equals to UPDATED_DESCRIPTION
        defaultShopImageShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllShopImagesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultShopImageShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the shopImageList where description equals to UPDATED_DESCRIPTION
        defaultShopImageShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllShopImagesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        // Get all the shopImageList where description is not null
        defaultShopImageShouldBeFound("description.specified=true");

        // Get all the shopImageList where description is null
        defaultShopImageShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllShopImagesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        shopImage.setProduct(product);
        shopImageRepository.saveAndFlush(shopImage);
        Long productId = product.getId();

        // Get all the shopImageList where product equals to productId
        defaultShopImageShouldBeFound("productId.equals=" + productId);

        // Get all the shopImageList where product equals to productId + 1
        defaultShopImageShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShopImageShouldBeFound(String filter) throws Exception {
        restShopImageMockMvc.perform(get("/api/shop-images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].herstArtNr").value(hasItem(DEFAULT_HERST_ART_NR)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restShopImageMockMvc.perform(get("/api/shop-images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShopImageShouldNotBeFound(String filter) throws Exception {
        restShopImageMockMvc.perform(get("/api/shop-images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShopImageMockMvc.perform(get("/api/shop-images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingShopImage() throws Exception {
        // Get the shopImage
        restShopImageMockMvc.perform(get("/api/shop-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShopImage() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        int databaseSizeBeforeUpdate = shopImageRepository.findAll().size();

        // Update the shopImage
        ShopImage updatedShopImage = shopImageRepository.findById(shopImage.getId()).get();
        // Disconnect from session so that the updates on updatedShopImage are not directly saved in db
        em.detach(updatedShopImage);
        updatedShopImage
            .name(UPDATED_NAME)
            .herstArtNr(UPDATED_HERST_ART_NR)
            .order(UPDATED_ORDER)
            .size(UPDATED_SIZE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ShopImageDTO shopImageDTO = shopImageMapper.toDto(updatedShopImage);

        restShopImageMockMvc.perform(put("/api/shop-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopImageDTO)))
            .andExpect(status().isOk());

        // Validate the ShopImage in the database
        List<ShopImage> shopImageList = shopImageRepository.findAll();
        assertThat(shopImageList).hasSize(databaseSizeBeforeUpdate);
        ShopImage testShopImage = shopImageList.get(shopImageList.size() - 1);
        assertThat(testShopImage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShopImage.getHerstArtNr()).isEqualTo(UPDATED_HERST_ART_NR);
        assertThat(testShopImage.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testShopImage.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testShopImage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testShopImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testShopImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingShopImage() throws Exception {
        int databaseSizeBeforeUpdate = shopImageRepository.findAll().size();

        // Create the ShopImage
        ShopImageDTO shopImageDTO = shopImageMapper.toDto(shopImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShopImageMockMvc.perform(put("/api/shop-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShopImage in the database
        List<ShopImage> shopImageList = shopImageRepository.findAll();
        assertThat(shopImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShopImage() throws Exception {
        // Initialize the database
        shopImageRepository.saveAndFlush(shopImage);

        int databaseSizeBeforeDelete = shopImageRepository.findAll().size();

        // Delete the shopImage
        restShopImageMockMvc.perform(delete("/api/shop-images/{id}", shopImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShopImage> shopImageList = shopImageRepository.findAll();
        assertThat(shopImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShopImage.class);
        ShopImage shopImage1 = new ShopImage();
        shopImage1.setId(1L);
        ShopImage shopImage2 = new ShopImage();
        shopImage2.setId(shopImage1.getId());
        assertThat(shopImage1).isEqualTo(shopImage2);
        shopImage2.setId(2L);
        assertThat(shopImage1).isNotEqualTo(shopImage2);
        shopImage1.setId(null);
        assertThat(shopImage1).isNotEqualTo(shopImage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShopImageDTO.class);
        ShopImageDTO shopImageDTO1 = new ShopImageDTO();
        shopImageDTO1.setId(1L);
        ShopImageDTO shopImageDTO2 = new ShopImageDTO();
        assertThat(shopImageDTO1).isNotEqualTo(shopImageDTO2);
        shopImageDTO2.setId(shopImageDTO1.getId());
        assertThat(shopImageDTO1).isEqualTo(shopImageDTO2);
        shopImageDTO2.setId(2L);
        assertThat(shopImageDTO1).isNotEqualTo(shopImageDTO2);
        shopImageDTO1.setId(null);
        assertThat(shopImageDTO1).isNotEqualTo(shopImageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shopImageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shopImageMapper.fromId(null)).isNull();
    }
}
