package net.generica.store.service.impl;

import net.generica.store.service.ProductReferenceService;
import net.generica.store.domain.ProductReference;
import net.generica.store.repository.ProductReferenceRepository;
import net.generica.store.service.dto.ProductReferenceDTO;
import net.generica.store.service.mapper.ProductReferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductReference}.
 */
@Service
@Transactional
public class ProductReferenceServiceImpl implements ProductReferenceService {

    private final Logger log = LoggerFactory.getLogger(ProductReferenceServiceImpl.class);

    private final ProductReferenceRepository productReferenceRepository;

    private final ProductReferenceMapper productReferenceMapper;

    public ProductReferenceServiceImpl(ProductReferenceRepository productReferenceRepository, ProductReferenceMapper productReferenceMapper) {
        this.productReferenceRepository = productReferenceRepository;
        this.productReferenceMapper = productReferenceMapper;
    }

    /**
     * Save a productReference.
     *
     * @param productReferenceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductReferenceDTO save(ProductReferenceDTO productReferenceDTO) {
        log.debug("Request to save ProductReference : {}", productReferenceDTO);
        ProductReference productReference = productReferenceMapper.toEntity(productReferenceDTO);
        productReference = productReferenceRepository.save(productReference);
        return productReferenceMapper.toDto(productReference);
    }

    /**
     * Get all the productReferences.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductReferenceDTO> findAll() {
        log.debug("Request to get all ProductReferences");
        return productReferenceRepository.findAll().stream()
            .map(productReferenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productReference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductReferenceDTO> findOne(Long id) {
        log.debug("Request to get ProductReference : {}", id);
        return productReferenceRepository.findById(id)
            .map(productReferenceMapper::toDto);
    }

    /**
     * Delete the productReference by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductReference : {}", id);
        productReferenceRepository.deleteById(id);
    }
}
