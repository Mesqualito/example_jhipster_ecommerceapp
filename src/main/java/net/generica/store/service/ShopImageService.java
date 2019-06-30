package net.generica.store.service;

import net.generica.store.domain.ShopImage;
import net.generica.store.repository.ShopImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ShopImage}.
 */
@Service
@Transactional
public class ShopImageService {

    private final Logger log = LoggerFactory.getLogger(ShopImageService.class);

    private final ShopImageRepository shopImageRepository;

    public ShopImageService(ShopImageRepository shopImageRepository) {
        this.shopImageRepository = shopImageRepository;
    }

    /**
     * Save a shopImage.
     *
     * @param shopImage the entity to save.
     * @return the persisted entity.
     */
    public ShopImage save(ShopImage shopImage) {
        log.debug("Request to save ShopImage : {}", shopImage);
        return shopImageRepository.save(shopImage);
    }

    /**
     * Get all the shopImages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ShopImage> findAll() {
        log.debug("Request to get all ShopImages");
        return shopImageRepository.findAll();
    }


    /**
     * Get one shopImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShopImage> findOne(Long id) {
        log.debug("Request to get ShopImage : {}", id);
        return shopImageRepository.findById(id);
    }

    /**
     * Delete the shopImage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ShopImage : {}", id);
        shopImageRepository.deleteById(id);
    }
}
