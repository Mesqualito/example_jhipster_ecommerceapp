package net.generica.store.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import net.generica.store.domain.Shipment;
import net.generica.store.domain.*; // for static metamodels
import net.generica.store.repository.ShipmentRepository;
import net.generica.store.service.dto.ShipmentCriteria;
import net.generica.store.service.dto.ShipmentDTO;
import net.generica.store.service.mapper.ShipmentMapper;

/**
 * Service for executing complex queries for Shipment entities in the database.
 * The main input is a {@link ShipmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShipmentDTO} or a {@link Page} of {@link ShipmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShipmentQueryService extends QueryService<Shipment> {

    private final Logger log = LoggerFactory.getLogger(ShipmentQueryService.class);

    private final ShipmentRepository shipmentRepository;

    private final ShipmentMapper shipmentMapper;

    public ShipmentQueryService(ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
    }

    /**
     * Return a {@link List} of {@link ShipmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShipmentDTO> findByCriteria(ShipmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Shipment> specification = createSpecification(criteria);
        return shipmentMapper.toDto(shipmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShipmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShipmentDTO> findByCriteria(ShipmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Shipment> specification = createSpecification(criteria);
        return shipmentRepository.findAll(specification, page)
            .map(shipmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShipmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Shipment> specification = createSpecification(criteria);
        return shipmentRepository.count(specification);
    }

    /**
     * Function to convert ShipmentCriteria to a {@link Specification}
     */
    private Specification<Shipment> createSpecification(ShipmentCriteria criteria) {
        Specification<Shipment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Shipment_.id));
            }
            if (criteria.getTrackingCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrackingCode(), Shipment_.trackingCode));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Shipment_.date));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), Shipment_.details));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(Shipment_.invoice, JoinType.LEFT).get(Invoice_.id)));
            }
        }
        return specification;
    }
}
