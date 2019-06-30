package net.generica.store.service;

import net.generica.store.domain.ProductSubstitution;
import net.generica.store.repository.ProductSubstitutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductSubstitution}.
 */
@Service
@Transactional
public class ProductSubstitutionService {

    private final Logger log = LoggerFactory.getLogger(ProductSubstitutionService.class);

    private final ProductSubstitutionRepository productSubstitutionRepository;

    public ProductSubstitutionService(ProductSubstitutionRepository productSubstitutionRepository) {
        this.productSubstitutionRepository = productSubstitutionRepository;
    }

    /**
     * Save a productSubstitution.
     *
     * @param productSubstitution the entity to save.
     * @return the persisted entity.
     */
    public ProductSubstitution save(ProductSubstitution productSubstitution) {
        log.debug("Request to save ProductSubstitution : {}", productSubstitution);
        return productSubstitutionRepository.save(productSubstitution);
    }

    /**
     * Get all the productSubstitutions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductSubstitution> findAll() {
        log.debug("Request to get all ProductSubstitutions");
        return productSubstitutionRepository.findAll();
    }


    /**
     * Get one productSubstitution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductSubstitution> findOne(Long id) {
        log.debug("Request to get ProductSubstitution : {}", id);
        return productSubstitutionRepository.findById(id);
    }

    /**
     * Delete the productSubstitution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductSubstitution : {}", id);
        productSubstitutionRepository.deleteById(id);
    }
}
