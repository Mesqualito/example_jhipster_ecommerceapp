package net.generica.store.web.rest;

import net.generica.store.domain.ShopImage;
import net.generica.store.service.ShopImageService;
import net.generica.store.web.rest.errors.BadRequestAlertException;
import net.generica.store.service.dto.ShopImageCriteria;
import net.generica.store.service.ShopImageQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.generica.store.domain.ShopImage}.
 */
@RestController
@RequestMapping("/api")
public class ShopImageResource {

    private final Logger log = LoggerFactory.getLogger(ShopImageResource.class);

    private static final String ENTITY_NAME = "shopImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShopImageService shopImageService;

    private final ShopImageQueryService shopImageQueryService;

    public ShopImageResource(ShopImageService shopImageService, ShopImageQueryService shopImageQueryService) {
        this.shopImageService = shopImageService;
        this.shopImageQueryService = shopImageQueryService;
    }

    /**
     * {@code POST  /shop-images} : Create a new shopImage.
     *
     * @param shopImage the shopImage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shopImage, or with status {@code 400 (Bad Request)} if the shopImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shop-images")
    public ResponseEntity<ShopImage> createShopImage(@Valid @RequestBody ShopImage shopImage) throws URISyntaxException {
        log.debug("REST request to save ShopImage : {}", shopImage);
        if (shopImage.getId() != null) {
            throw new BadRequestAlertException("A new shopImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShopImage result = shopImageService.save(shopImage);
        return ResponseEntity.created(new URI("/api/shop-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shop-images} : Updates an existing shopImage.
     *
     * @param shopImage the shopImage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shopImage,
     * or with status {@code 400 (Bad Request)} if the shopImage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shopImage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shop-images")
    public ResponseEntity<ShopImage> updateShopImage(@Valid @RequestBody ShopImage shopImage) throws URISyntaxException {
        log.debug("REST request to update ShopImage : {}", shopImage);
        if (shopImage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShopImage result = shopImageService.save(shopImage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shopImage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shop-images} : get all the shopImages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shopImages in body.
     */
    @GetMapping("/shop-images")
    public ResponseEntity<List<ShopImage>> getAllShopImages(ShopImageCriteria criteria) {
        log.debug("REST request to get ShopImages by criteria: {}", criteria);
        List<ShopImage> entityList = shopImageQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /shop-images/count} : count all the shopImages.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/shop-images/count")
    public ResponseEntity<Long> countShopImages(ShopImageCriteria criteria) {
        log.debug("REST request to count ShopImages by criteria: {}", criteria);
        return ResponseEntity.ok().body(shopImageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /shop-images/:id} : get the "id" shopImage.
     *
     * @param id the id of the shopImage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shopImage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shop-images/{id}")
    public ResponseEntity<ShopImage> getShopImage(@PathVariable Long id) {
        log.debug("REST request to get ShopImage : {}", id);
        Optional<ShopImage> shopImage = shopImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shopImage);
    }

    /**
     * {@code DELETE  /shop-images/:id} : delete the "id" shopImage.
     *
     * @param id the id of the shopImage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shop-images/{id}")
    public ResponseEntity<Void> deleteShopImage(@PathVariable Long id) {
        log.debug("REST request to delete ShopImage : {}", id);
        shopImageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
