package net.generica.store.service;

import net.generica.store.service.dto.ProductReferenceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link net.generica.store.domain.ProductReference}.
 */
public interface ProductReferenceService {

    /**
     * Save a productReference.
     *
     * @param productReferenceDTO the entity to save.
     * @return the persisted entity.
     */
    ProductReferenceDTO save(ProductReferenceDTO productReferenceDTO);

    /**
     * Get all the productReferences.
     *
     * @return the list of entities.
     */
    List<ProductReferenceDTO> findAll();


    /**
     * Get the "id" productReference.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductReferenceDTO> findOne(Long id);

    /**
     * Delete the "id" productReference.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
