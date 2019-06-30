package net.generica.store.web.rest;

import net.generica.store.domain.ProductSubstitution;
import net.generica.store.service.ProductSubstitutionService;
import net.generica.store.web.rest.errors.BadRequestAlertException;
import net.generica.store.service.dto.ProductSubstitutionCriteria;
import net.generica.store.service.ProductSubstitutionQueryService;

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
 * REST controller for managing {@link net.generica.store.domain.ProductSubstitution}.
 */
@RestController
@RequestMapping("/api")
public class ProductSubstitutionResource {

    private final Logger log = LoggerFactory.getLogger(ProductSubstitutionResource.class);

    private static final String ENTITY_NAME = "productSubstitution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSubstitutionService productSubstitutionService;

    private final ProductSubstitutionQueryService productSubstitutionQueryService;

    public ProductSubstitutionResource(ProductSubstitutionService productSubstitutionService, ProductSubstitutionQueryService productSubstitutionQueryService) {
        this.productSubstitutionService = productSubstitutionService;
        this.productSubstitutionQueryService = productSubstitutionQueryService;
    }

    /**
     * {@code POST  /product-substitutions} : Create a new productSubstitution.
     *
     * @param productSubstitution the productSubstitution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSubstitution, or with status {@code 400 (Bad Request)} if the productSubstitution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-substitutions")
    public ResponseEntity<ProductSubstitution> createProductSubstitution(@Valid @RequestBody ProductSubstitution productSubstitution) throws URISyntaxException {
        log.debug("REST request to save ProductSubstitution : {}", productSubstitution);
        if (productSubstitution.getId() != null) {
            throw new BadRequestAlertException("A new productSubstitution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSubstitution result = productSubstitutionService.save(productSubstitution);
        return ResponseEntity.created(new URI("/api/product-substitutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-substitutions} : Updates an existing productSubstitution.
     *
     * @param productSubstitution the productSubstitution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSubstitution,
     * or with status {@code 400 (Bad Request)} if the productSubstitution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSubstitution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-substitutions")
    public ResponseEntity<ProductSubstitution> updateProductSubstitution(@Valid @RequestBody ProductSubstitution productSubstitution) throws URISyntaxException {
        log.debug("REST request to update ProductSubstitution : {}", productSubstitution);
        if (productSubstitution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSubstitution result = productSubstitutionService.save(productSubstitution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSubstitution.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-substitutions} : get all the productSubstitutions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSubstitutions in body.
     */
    @GetMapping("/product-substitutions")
    public ResponseEntity<List<ProductSubstitution>> getAllProductSubstitutions(ProductSubstitutionCriteria criteria) {
        log.debug("REST request to get ProductSubstitutions by criteria: {}", criteria);
        List<ProductSubstitution> entityList = productSubstitutionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-substitutions/count} : count all the productSubstitutions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-substitutions/count")
    public ResponseEntity<Long> countProductSubstitutions(ProductSubstitutionCriteria criteria) {
        log.debug("REST request to count ProductSubstitutions by criteria: {}", criteria);
        return ResponseEntity.ok().body(productSubstitutionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-substitutions/:id} : get the "id" productSubstitution.
     *
     * @param id the id of the productSubstitution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSubstitution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-substitutions/{id}")
    public ResponseEntity<ProductSubstitution> getProductSubstitution(@PathVariable Long id) {
        log.debug("REST request to get ProductSubstitution : {}", id);
        Optional<ProductSubstitution> productSubstitution = productSubstitutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSubstitution);
    }

    /**
     * {@code DELETE  /product-substitutions/:id} : delete the "id" productSubstitution.
     *
     * @param id the id of the productSubstitution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-substitutions/{id}")
    public ResponseEntity<Void> deleteProductSubstitution(@PathVariable Long id) {
        log.debug("REST request to delete ProductSubstitution : {}", id);
        productSubstitutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
