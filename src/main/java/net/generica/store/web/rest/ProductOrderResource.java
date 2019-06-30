package net.generica.store.web.rest;

import net.generica.store.service.ProductOrderService;
import net.generica.store.web.rest.errors.BadRequestAlertException;
import net.generica.store.service.dto.ProductOrderDTO;
import net.generica.store.service.dto.ProductOrderCriteria;
import net.generica.store.service.ProductOrderQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.generica.store.domain.ProductOrder}.
 */
@RestController
@RequestMapping("/api")
public class ProductOrderResource {

    private final Logger log = LoggerFactory.getLogger(ProductOrderResource.class);

    private static final String ENTITY_NAME = "productOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductOrderService productOrderService;

    private final ProductOrderQueryService productOrderQueryService;

    public ProductOrderResource(ProductOrderService productOrderService, ProductOrderQueryService productOrderQueryService) {
        this.productOrderService = productOrderService;
        this.productOrderQueryService = productOrderQueryService;
    }

    /**
     * {@code POST  /product-orders} : Create a new productOrder.
     *
     * @param productOrderDTO the productOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productOrderDTO, or with status {@code 400 (Bad Request)} if the productOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-orders")
    public ResponseEntity<ProductOrderDTO> createProductOrder(@Valid @RequestBody ProductOrderDTO productOrderDTO) throws URISyntaxException {
        log.debug("REST request to save ProductOrder : {}", productOrderDTO);
        if (productOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new productOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOrderDTO result = productOrderService.save(productOrderDTO);
        return ResponseEntity.created(new URI("/api/product-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-orders} : Updates an existing productOrder.
     *
     * @param productOrderDTO the productOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOrderDTO,
     * or with status {@code 400 (Bad Request)} if the productOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-orders")
    public ResponseEntity<ProductOrderDTO> updateProductOrder(@Valid @RequestBody ProductOrderDTO productOrderDTO) throws URISyntaxException {
        log.debug("REST request to update ProductOrder : {}", productOrderDTO);
        if (productOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductOrderDTO result = productOrderService.save(productOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-orders} : get all the productOrders.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productOrders in body.
     */
    @GetMapping("/product-orders")
    public ResponseEntity<List<ProductOrderDTO>> getAllProductOrders(ProductOrderCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ProductOrders by criteria: {}", criteria);
        Page<ProductOrderDTO> page = productOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /product-orders/count} : count all the productOrders.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-orders/count")
    public ResponseEntity<Long> countProductOrders(ProductOrderCriteria criteria) {
        log.debug("REST request to count ProductOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(productOrderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-orders/:id} : get the "id" productOrder.
     *
     * @param id the id of the productOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-orders/{id}")
    public ResponseEntity<ProductOrderDTO> getProductOrder(@PathVariable Long id) {
        log.debug("REST request to get ProductOrder : {}", id);
        Optional<ProductOrderDTO> productOrderDTO = productOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOrderDTO);
    }

    /**
     * {@code DELETE  /product-orders/:id} : delete the "id" productOrder.
     *
     * @param id the id of the productOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-orders/{id}")
    public ResponseEntity<Void> deleteProductOrder(@PathVariable Long id) {
        log.debug("REST request to delete ProductOrder : {}", id);
        productOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
