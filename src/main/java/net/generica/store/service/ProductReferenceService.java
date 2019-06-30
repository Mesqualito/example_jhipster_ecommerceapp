package net.generica.store.service;

import net.generica.store.domain.ProductReference;
import net.generica.store.repository.ProductReferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductReference}.
 */
@Service
@Transactional
public class ProductReferenceService {

    private final Logger log = LoggerFactory.getLogger(ProductReferenceService.class);

    private final ProductReferenceRepository productReferenceRepository;

    public ProductReferenceService(ProductReferenceRepository productReferenceRepository) {
        this.productReferenceRepository = productReferenceRepository;
    }

    /**
     * Save a productReference.
     *
     * @param productReference the entity to save.
     * @return the persisted entity.
     */
    public ProductReference save(ProductReference productReference) {
        log.debug("Request to save ProductReference : {}", productReference);
        return productReferenceRepository.save(productReference);
    }

    /**
     * Get all the productReferences.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductReference> findAll() {
        log.debug("Request to get all ProductReferences");
        return productReferenceRepository.findAll();
    }


    /**
     * Get one productReference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductReference> findOne(Long id) {
        log.debug("Request to get ProductReference : {}", id);
        return productReferenceRepository.findById(id);
    }

    /**
     * Delete the productReference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductReference : {}", id);
        productReferenceRepository.deleteById(id);
    }
}
