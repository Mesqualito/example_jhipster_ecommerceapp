package net.generica.store.service;

import net.generica.store.service.dto.ShipmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link net.generica.store.domain.Shipment}.
 */
public interface ShipmentService {

    /**
     * Save a shipment.
     *
     * @param shipmentDTO the entity to save.
     * @return the persisted entity.
     */
    ShipmentDTO save(ShipmentDTO shipmentDTO);

    /**
     * Get all the shipments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" shipment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipmentDTO> findOne(Long id);

    /**
     * Delete the "id" shipment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
