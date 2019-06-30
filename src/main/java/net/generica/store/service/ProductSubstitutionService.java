package net.generica.store.service;

import net.generica.store.service.dto.ProductSubstitutionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link net.generica.store.domain.ProductSubstitution}.
 */
public interface ProductSubstitutionService {

    /**
     * Save a productSubstitution.
     *
     * @param productSubstitutionDTO the entity to save.
     * @return the persisted entity.
     */
    ProductSubstitutionDTO save(ProductSubstitutionDTO productSubstitutionDTO);

    /**
     * Get all the productSubstitutions.
     *
     * @return the list of entities.
     */
    List<ProductSubstitutionDTO> findAll();


    /**
     * Get the "id" productSubstitution.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSubstitutionDTO> findOne(Long id);

    /**
     * Delete the "id" productSubstitution.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
