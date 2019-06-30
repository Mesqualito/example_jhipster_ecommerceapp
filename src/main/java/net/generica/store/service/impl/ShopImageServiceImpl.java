package net.generica.store.service.impl;

import net.generica.store.service.ShopImageService;
import net.generica.store.domain.ShopImage;
import net.generica.store.repository.ShopImageRepository;
import net.generica.store.service.dto.ShopImageDTO;
import net.generica.store.service.mapper.ShopImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ShopImage}.
 */
@Service
@Transactional
public class ShopImageServiceImpl implements ShopImageService {

    private final Logger log = LoggerFactory.getLogger(ShopImageServiceImpl.class);

    private final ShopImageRepository shopImageRepository;

    private final ShopImageMapper shopImageMapper;

    public ShopImageServiceImpl(ShopImageRepository shopImageRepository, ShopImageMapper shopImageMapper) {
        this.shopImageRepository = shopImageRepository;
        this.shopImageMapper = shopImageMapper;
    }

    /**
     * Save a shopImage.
     *
     * @param shopImageDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShopImageDTO save(ShopImageDTO shopImageDTO) {
        log.debug("Request to save ShopImage : {}", shopImageDTO);
        ShopImage shopImage = shopImageMapper.toEntity(shopImageDTO);
        shopImage = shopImageRepository.save(shopImage);
        return shopImageMapper.toDto(shopImage);
    }

    /**
     * Get all the shopImages.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShopImageDTO> findAll() {
        log.debug("Request to get all ShopImages");
        return shopImageRepository.findAll().stream()
            .map(shopImageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shopImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShopImageDTO> findOne(Long id) {
        log.debug("Request to get ShopImage : {}", id);
        return shopImageRepository.findById(id)
            .map(shopImageMapper::toDto);
    }

    /**
     * Delete the shopImage by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShopImage : {}", id);
        shopImageRepository.deleteById(id);
    }
}
