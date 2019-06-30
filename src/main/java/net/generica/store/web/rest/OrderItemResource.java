package net.generica.store.web.rest;

import net.generica.store.service.OrderItemService;
import net.generica.store.web.rest.errors.BadRequestAlertException;
import net.generica.store.service.dto.OrderItemDTO;
import net.generica.store.service.dto.OrderItemCriteria;
import net.generica.store.service.OrderItemQueryService;

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
 * REST controller for managing {@link net.generica.store.domain.OrderItem}.
 */
@RestController
@RequestMapping("/api")
public class OrderItemResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemResource.class);

    private static final String ENTITY_NAME = "orderItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderItemService orderItemService;

    private final OrderItemQueryService orderItemQueryService;

    public OrderItemResource(OrderItemService orderItemService, OrderItemQueryService orderItemQueryService) {
        this.orderItemService = orderItemService;
        this.orderItemQueryService = orderItemQueryService;
    }

    /**
     * {@code POST  /order-items} : Create a new orderItem.
     *
     * @param orderItemDTO the orderItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderItemDTO, or with status {@code 400 (Bad Request)} if the orderItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-items")
    public ResponseEntity<OrderItemDTO> createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) throws URISyntaxException {
        log.debug("REST request to save OrderItem : {}", orderItemDTO);
        if (orderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.created(new URI("/api/order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-items} : Updates an existing orderItem.
     *
     * @param orderItemDTO the orderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderItemDTO,
     * or with status {@code 400 (Bad Request)} if the orderItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-items")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) throws URISyntaxException {
        log.debug("REST request to update OrderItem : {}", orderItemDTO);
        if (orderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-items} : get all the orderItems.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderItems in body.
     */
    @GetMapping("/order-items")
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems(OrderItemCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get OrderItems by criteria: {}", criteria);
        Page<OrderItemDTO> page = orderItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /order-items/count} : count all the orderItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/order-items/count")
    public ResponseEntity<Long> countOrderItems(OrderItemCriteria criteria) {
        log.debug("REST request to count OrderItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-items/:id} : get the "id" orderItem.
     *
     * @param id the id of the orderItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-items/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItem(@PathVariable Long id) {
        log.debug("REST request to get OrderItem : {}", id);
        Optional<OrderItemDTO> orderItemDTO = orderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderItemDTO);
    }

    /**
     * {@code DELETE  /order-items/:id} : delete the "id" orderItem.
     *
     * @param id the id of the orderItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-items/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete OrderItem : {}", id);
        orderItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
