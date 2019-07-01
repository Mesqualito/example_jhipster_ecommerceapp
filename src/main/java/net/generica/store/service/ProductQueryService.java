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

import net.generica.store.domain.Product;
import net.generica.store.domain.*; // for static metamodels
import net.generica.store.repository.ProductRepository;
import net.generica.store.service.dto.ProductCriteria;
import net.generica.store.service.dto.ProductDTO;
import net.generica.store.service.mapper.ProductMapper;

/**
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductDTO} or a {@link Page} of {@link ProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductQueryService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Return a {@link List} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productMapper.toDto(productRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page)
            .map(productMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getErpId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErpId(), Product_.erpId));
            }
            if (criteria.getRefined() != null) {
                specification = specification.and(buildSpecification(criteria.getRefined(), Product_.refined));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Product_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Product_.description));
            }
            if (criteria.getHerstArtNr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHerstArtNr(), Product_.herstArtNr));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Product_.price));
            }
            if (criteria.getKatalogOnly() != null) {
                specification = specification.and(buildSpecification(criteria.getKatalogOnly(), Product_.katalogOnly));
            }
            if (criteria.getShopImageId() != null) {
                specification = specification.and(buildSpecification(criteria.getShopImageId(),
                    root -> root.join(Product_.shopImages, JoinType.LEFT).get(ShopImage_.id)));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenceId(),
                    root -> root.join(Product_.references, JoinType.LEFT).get(ProductReference_.id)));
            }
            if (criteria.getProductSubstitutionId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductSubstitutionId(),
                    root -> root.join(Product_.productSubstitutions, JoinType.LEFT).get(ProductSubstitution_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(Product_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
        }
        return specification;
    }
}
