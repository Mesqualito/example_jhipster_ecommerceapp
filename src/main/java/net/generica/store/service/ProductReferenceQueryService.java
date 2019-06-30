package net.generica.store.service;

import io.github.jhipster.service.QueryService;
import net.generica.store.domain.ProductReference;
import net.generica.store.domain.ProductReference_;
import net.generica.store.domain.Product_;
import net.generica.store.repository.ProductReferenceRepository;
import net.generica.store.service.dto.ProductReferenceCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link ProductReference} entities in the database.
 * The main input is a {@link ProductReferenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductReference} or a {@link Page} of {@link ProductReference} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductReferenceQueryService extends QueryService<ProductReference> {

    private final Logger log = LoggerFactory.getLogger(ProductReferenceQueryService.class);

    private final ProductReferenceRepository productReferenceRepository;

    public ProductReferenceQueryService(ProductReferenceRepository productReferenceRepository) {
        this.productReferenceRepository = productReferenceRepository;
    }

    /**
     * Return a {@link List} of {@link ProductReference} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductReference> findByCriteria(ProductReferenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductReference> specification = createSpecification(criteria);
        return productReferenceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductReference} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductReference> findByCriteria(ProductReferenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductReference> specification = createSpecification(criteria);
        return productReferenceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductReferenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductReference> specification = createSpecification(criteria);
        return productReferenceRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ProductReference> createSpecification(ProductReferenceCriteria criteria) {
        Specification<ProductReference> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductReference_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductReference_.name));
            }
            if (criteria.getRefName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefName(), ProductReference_.refName));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), ProductReference_.reference));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ProductReference_.type));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductReference_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
