package net.generica.store.service;

import net.generica.store.service.dto.ShopImageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link net.generica.store.domain.ShopImage}.
 */
public interface ShopImageService {

    /**
     * Save a shopImage.
     *
     * @param shopImageDTO the entity to save.
     * @return the persisted entity.
     */
    ShopImageDTO save(ShopImageDTO shopImageDTO);

    /**
     * Get all the shopImages.
     *
     * @return the list of entities.
     */
    List<ShopImageDTO> findAll();


    /**
     * Get the "id" shopImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShopImageDTO> findOne(Long id);

    /**
     * Delete the "id" shopImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
