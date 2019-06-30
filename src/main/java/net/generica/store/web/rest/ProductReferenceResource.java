package net.generica.store.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.generica.store.domain.ProductReference;
import net.generica.store.service.ProductReferenceQueryService;
import net.generica.store.service.ProductReferenceService;
import net.generica.store.service.dto.ProductReferenceCriteria;
import net.generica.store.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link net.generica.store.domain.ProductReference}.
 */
@RestController
@RequestMapping("/api")
public class ProductReferenceResource {

    private final Logger log = LoggerFactory.getLogger(ProductReferenceResource.class);

    private static final String ENTITY_NAME = "productReference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductReferenceService productReferenceService;

    private final ProductReferenceQueryService productReferenceQueryService;

    public ProductReferenceResource(ProductReferenceService productReferenceService, ProductReferenceQueryService productReferenceQueryService) {
        this.productReferenceService = productReferenceService;
        this.productReferenceQueryService = productReferenceQueryService;
    }

    /**
     * {@code POST  /product-references} : Create a new productReference.
     *
     * @param productReference the productReference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productReference, or with status {@code 400 (Bad Request)} if the productReference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-references")
    public ResponseEntity<ProductReference> createProductReference(@Valid @RequestBody ProductReference productReference) throws URISyntaxException {
        log.debug("REST request to save ProductReference : {}", productReference);
        if (productReference.getId() != null) {
            throw new BadRequestAlertException("A new productReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductReference result = productReferenceService.save(productReference);
        return ResponseEntity.created(new URI("/api/product-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-references} : Updates an existing productReference.
     *
     * @param productReference the productReference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productReference,
     * or with status {@code 400 (Bad Request)} if the productReference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productReference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-references")
    public ResponseEntity<ProductReference> updateProductReference(@Valid @RequestBody ProductReference productReference) throws URISyntaxException {
        log.debug("REST request to update ProductReference : {}", productReference);
        if (productReference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductReference result = productReferenceService.save(productReference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productReference.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-references} : get all the productReferences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productReferences in body.
     */
    @GetMapping("/product-references")
    public ResponseEntity<List<ProductReference>> getAllProductReferences(ProductReferenceCriteria criteria) {
        log.debug("REST request to get ProductReferences by criteria: {}", criteria);
        List<ProductReference> entityList = productReferenceQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-references/count} : count all the productReferences.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-references/count")
    public ResponseEntity<Long> countProductReferences(ProductReferenceCriteria criteria) {
        log.debug("REST request to count ProductReferences by criteria: {}", criteria);
        return ResponseEntity.ok().body(productReferenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-references/:id} : get the "id" productReference.
     *
     * @param id the id of the productReference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productReference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-references/{id}")
    public ResponseEntity<ProductReference> getProductReference(@PathVariable Long id) {
        log.debug("REST request to get ProductReference : {}", id);
        Optional<ProductReference> productReference = productReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productReference);
    }

    /**
     * {@code DELETE  /product-references/:id} : delete the "id" productReference.
     *
     * @param id the id of the productReference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-references/{id}")
    public ResponseEntity<Void> deleteProductReference(@PathVariable Long id) {
        log.debug("REST request to delete ProductReference : {}", id);
        productReferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
