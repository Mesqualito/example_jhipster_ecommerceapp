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

import net.generica.store.domain.ProductSubstitution;
import net.generica.store.domain.*; // for static metamodels
import net.generica.store.repository.ProductSubstitutionRepository;
import net.generica.store.service.dto.ProductSubstitutionCriteria;
import net.generica.store.service.dto.ProductSubstitutionDTO;
import net.generica.store.service.mapper.ProductSubstitutionMapper;

/**
 * Service for executing complex queries for {@link ProductSubstitution} entities in the database.
 * The main input is a {@link ProductSubstitutionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductSubstitutionDTO} or a {@link Page} of {@link ProductSubstitutionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductSubstitutionQueryService extends QueryService<ProductSubstitution> {

    private final Logger log = LoggerFactory.getLogger(ProductSubstitutionQueryService.class);

    private final ProductSubstitutionRepository productSubstitutionRepository;

    private final ProductSubstitutionMapper productSubstitutionMapper;

    public ProductSubstitutionQueryService(ProductSubstitutionRepository productSubstitutionRepository, ProductSubstitutionMapper productSubstitutionMapper) {
        this.productSubstitutionRepository = productSubstitutionRepository;
        this.productSubstitutionMapper = productSubstitutionMapper;
    }

    /**
     * Return a {@link List} of {@link ProductSubstitutionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductSubstitutionDTO> findByCriteria(ProductSubstitutionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductSubstitution> specification = createSpecification(criteria);
        return productSubstitutionMapper.toDto(productSubstitutionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductSubstitutionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductSubstitutionDTO> findByCriteria(ProductSubstitutionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductSubstitution> specification = createSpecification(criteria);
        return productSubstitutionRepository.findAll(specification, page)
            .map(productSubstitutionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductSubstitutionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductSubstitution> specification = createSpecification(criteria);
        return productSubstitutionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ProductSubstitution> createSpecification(ProductSubstitutionCriteria criteria) {
        Specification<ProductSubstitution> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductSubstitution_.id));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), ProductSubstitution_.productName));
            }
            if (criteria.getExchangeable() != null) {
                specification = specification.and(buildSpecification(criteria.getExchangeable(), ProductSubstitution_.exchangeable));
            }
            if (criteria.getChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getChecked(), ProductSubstitution_.checked));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductSubstitution_.products, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
