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

import net.generica.store.domain.Invoice;
import net.generica.store.domain.*; // for static metamodels
import net.generica.store.repository.InvoiceRepository;
import net.generica.store.service.dto.InvoiceCriteria;
import net.generica.store.service.dto.InvoiceDTO;
import net.generica.store.service.mapper.InvoiceMapper;

/**
 * Service for executing complex queries for Invoice entities in the database.
 * The main input is a {@link InvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoiceDTO} or a {@link Page} of {@link InvoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceQueryService extends QueryService<Invoice> {

    private final Logger log = LoggerFactory.getLogger(InvoiceQueryService.class);

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    public InvoiceQueryService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    /**
     * Return a {@link List} of {@link InvoiceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceDTO> findByCriteria(InvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceMapper.toDto(invoiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvoiceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceDTO> findByCriteria(InvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.findAll(specification, page)
            .map(invoiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.count(specification);
    }

    /**
     * Function to convert InvoiceCriteria to a {@link Specification}
     */
    private Specification<Invoice> createSpecification(InvoiceCriteria criteria) {
        Specification<Invoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Invoice_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Invoice_.date));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), Invoice_.details));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Invoice_.status));
            }
            if (criteria.getPaymentMethod() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMethod(), Invoice_.paymentMethod));
            }
            if (criteria.getPaymentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentDate(), Invoice_.paymentDate));
            }
            if (criteria.getPaymentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentAmount(), Invoice_.paymentAmount));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Invoice_.code));
            }
            if (criteria.getShipmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getShipmentId(),
                    root -> root.join(Invoice_.shipments, JoinType.LEFT).get(Shipment_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(Invoice_.order, JoinType.LEFT).get(ProductOrder_.id)));
            }
        }
        return specification;
    }
}
