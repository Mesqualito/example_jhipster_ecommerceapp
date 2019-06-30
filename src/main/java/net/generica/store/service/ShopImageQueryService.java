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

import net.generica.store.domain.ShopImage;
import net.generica.store.domain.*; // for static metamodels
import net.generica.store.repository.ShopImageRepository;
import net.generica.store.service.dto.ShopImageCriteria;
import net.generica.store.service.dto.ShopImageDTO;
import net.generica.store.service.mapper.ShopImageMapper;

/**
 * Service for executing complex queries for {@link ShopImage} entities in the database.
 * The main input is a {@link ShopImageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShopImageDTO} or a {@link Page} of {@link ShopImageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShopImageQueryService extends QueryService<ShopImage> {

    private final Logger log = LoggerFactory.getLogger(ShopImageQueryService.class);

    private final ShopImageRepository shopImageRepository;

    private final ShopImageMapper shopImageMapper;

    public ShopImageQueryService(ShopImageRepository shopImageRepository, ShopImageMapper shopImageMapper) {
        this.shopImageRepository = shopImageRepository;
        this.shopImageMapper = shopImageMapper;
    }

    /**
     * Return a {@link List} of {@link ShopImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShopImageDTO> findByCriteria(ShopImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShopImage> specification = createSpecification(criteria);
        return shopImageMapper.toDto(shopImageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShopImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShopImageDTO> findByCriteria(ShopImageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShopImage> specification = createSpecification(criteria);
        return shopImageRepository.findAll(specification, page)
            .map(shopImageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShopImageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShopImage> specification = createSpecification(criteria);
        return shopImageRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ShopImage> createSpecification(ShopImageCriteria criteria) {
        Specification<ShopImage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ShopImage_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ShopImage_.name));
            }
            if (criteria.getHerstArtNr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHerstArtNr(), ShopImage_.herstArtNr));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), ShopImage_.order));
            }
            if (criteria.getSize() != null) {
                specification = specification.and(buildSpecification(criteria.getSize(), ShopImage_.size));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ShopImage_.description));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ShopImage_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
